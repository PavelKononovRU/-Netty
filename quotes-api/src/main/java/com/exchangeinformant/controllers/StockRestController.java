package com.exchangeinformant.controllers;

import com.exchangeinformant.exception.ErrorCodes;
import com.exchangeinformant.exception.QuotesException;
import com.exchangeinformant.model.Stock;
import com.exchangeinformant.services.StockDbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by e-davidenko
 * Date: 12.01.2023
 * Time: 15:10
 */

@RestController
public class StockRestController {

    private final StockDbService stockDbService;

    public StockRestController(StockDbService stockDbService) {
        this.stockDbService = stockDbService;
    }

    @GetMapping("/stock")
    public ResponseEntity<?> getStock(@RequestParam("name") String secureCode) {
        Stock stock = stockDbService.getStock(secureCode);
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }

    @GetMapping("/stock/query")
    public ResponseEntity<?> getStockWithParameters(@RequestParam(name = "dateFrom", required = false, defaultValue = "#{T(java.time.LocalDateTime).now().toLocalDate().atStartOfDay()}") LocalDateTime dateFrom,
                                     @RequestParam(name = "dateTo", required = false, defaultValue = "#{T(java.time.LocalDateTime).now()}") LocalDateTime dateTo,
                                     @RequestParam("stock") String secureCode) {
        return new ResponseEntity<>(stockDbService.getStockByDate(secureCode, dateFrom, dateTo), HttpStatus.OK);
    }

    @GetMapping("/allWithDates")
    public ResponseEntity<?> getAllStocksByDates(@RequestParam(name = "dateFrom", required = false, defaultValue = "#{T(java.time.LocalDateTime).now().toLocalDate().atStartOfDay()}") LocalDateTime dateFrom,
                                                 @RequestParam(name = "dateTo", required = false, defaultValue = "#{T(java.time.LocalDateTime).now()}") LocalDateTime dateTo) {
        return new ResponseEntity<>(stockDbService.getAllStocksByDate(dateFrom, dateTo), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllStocks() {
        return new ResponseEntity<>(stockDbService.getAllStocks(), HttpStatus.OK);
    }
    @PostMapping("/availableStocks")
    public ResponseEntity<?> getAllAvailableStocks(@RequestBody List<String> securityCodes) {
        return new ResponseEntity<>(stockDbService.getAllAvailableStocksByCodes(securityCodes), HttpStatus.OK);
    }

    // ?????? ??????????????????????
    @GetMapping("/error")
    public ResponseEntity<?> getError() {
        throw new QuotesException(ErrorCodes.UPDATE_PROBLEM.name());
    }


}
