package com.example.gatewayservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;


@Configuration
@EnableCaching
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
public class SecurityConfig {

    private final SuccessHandler successHandler;

    private final csrfFilter csrfFilter;

    @Autowired
    public SecurityConfig(SuccessHandler successHandler, com.example.gatewayservice.config.csrfFilter csrfFilter) {
        this.successHandler = successHandler;
        this.csrfFilter = csrfFilter;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws RuntimeException {
        http

                //.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //.addFilterBefore(csrfFilter, SecurityWebFiltersOrder.CSRF)
                    .authorizeExchange()
                    .pathMatchers("/actuator/**")
                    .permitAll()
                .and()
                .oauth2Login(Customizer.withDefaults())
                    .authorizeExchange()
                    .anyExchange()
                    .authenticated()
                    .and()
                .logout(Customizer.withDefaults());
                //.csrf(csrf -> csrf.csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse()));

        return http.build();
    }

    @Bean
    public ServerLogoutSuccessHandler keycloakLogoutSuccessHandler(ReactiveClientRegistrationRepository repository) {

        OidcClientInitiatedServerLogoutSuccessHandler oidcLogoutSuccessHandler =
                new OidcClientInitiatedServerLogoutSuccessHandler(repository);

        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("http://localhost:8080/logout.html");

        return oidcLogoutSuccessHandler;
    }
}
