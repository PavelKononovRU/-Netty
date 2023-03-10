package com.exchangeinformant.configuration;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Created in IntelliJ
 * User: e-davidenko
 * Date: 23.12.2022
 * Time: 10:13
 */
@Configuration
@ConfigurationProperties(prefix = "webclient")
public class WebClientConfiguration {
    final int size = 10 * 1024 * 1024;
    final ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
            .build();

    @Bean
    public WebClient webClientWithTimeout() {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .compress(true)
                        .doOnConnected(connection -> connection
                                .addHandlerFirst(new ReadTimeoutHandler(5, TimeUnit.SECONDS))
                                .addHandlerFirst(new WriteTimeoutHandler(5, TimeUnit.SECONDS)))
                        .responseTimeout(Duration.ofSeconds(5))
                        .keepAlive(true)
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                        .followRedirect(true)))
                .exchangeStrategies(strategies)
                .build();
    }

}
