package com.exchangeinformant.controllers;

import com.exchangeinformant.configuration.TinkoffConfig;
import com.exchangeinformant.exception.QuotesException;
import com.exchangeinformant.model.Info;
import com.exchangeinformant.model.Stock;
import com.exchangeinformant.services.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;


@SpringBootTest(classes = TinkoffStockService.class)
@ContextConfiguration(classes = TinkoffConfig.class)
@ExtendWith(SpringExtension.class)
@Import(StockRestController.class)
@ComponentScan(basePackages = "com.exchangeinformant")
class StockRestControllerTest {
    private MockMvc mockMvc;
    @Mock
    private StockDbServiceImpl stockDbService;
    @InjectMocks
    private StockRestController stockRestController;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Stock stock;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(stockRestController).build();
        stock = new Stock("AAPL", "Apple" ,"USD", new ArrayList<>(){
            {
                add(new Info(1,34d, LocalDateTime.now(),"AAPL"));
            }
        });
    }
    @Test
    void shouldGetStock() throws Exception {
        when(stockDbService.getStock(Mockito.any())).thenReturn(stock);

        mockMvc.perform(get("/stock/?name=AAPL"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.issuer", is("Apple")))
                .andExpect(jsonPath("$.currency", is("USD")))
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void shouldGetAllStocks() throws Exception {
        when(stockDbService.getAllStocks()).thenReturn(Collections.singletonList(stock));
        mockMvc.perform(get("/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldGetAllAvailableStocks() throws Exception {
        List<String> codes = new ArrayList<>();
        codes.add("AAPL");
        when(stockDbService.getAllAvailableStocksByCodes(Mockito.anyList())).thenReturn(Collections.singletonList(stock));
        mockMvc.perform(post("/availableStocks")
                        .content(objectMapper.writeValueAsString(codes))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty());
    }
}