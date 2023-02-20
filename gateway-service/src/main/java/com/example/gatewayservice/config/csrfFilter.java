package com.example.gatewayservice.config;

import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
@Component
public class csrfFilter implements WebFilter {

/*    @Override
    Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var xsrfToken = exchange.getRequest().getCookies().getFirst("XSRF-TOKEN").getValue();
        exchange = exchange.mutate().request({header("X-XSRF-TOKEN", xsrfToken)
        }).build();
        chain.filter(exchange);
    }*/

/*    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        Mono<CsrfToken> token = (Mono<CsrfToken>) exchange.getAttributes().get(CsrfToken.class.getName());
        if (token != null) {
            return token.flatMap(t -> chain.filter(exchange));
        }
        return chain.filter(exchange);
    }*/

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        Mono<CsrfToken> token = (Mono<CsrfToken>) exchange.getAttributes().get(CsrfToken.class.getName());
        if (token != null) {
            return token.flatMap(t -> chain.filter(exchange));
        }
        return chain.filter(exchange);
    }
}
