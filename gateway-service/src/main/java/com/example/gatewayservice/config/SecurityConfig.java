package com.example.gatewayservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;


@Configuration
@EnableCaching
@EnableWebFluxSecurity
public class SecurityConfig {

    private final SuccessHandler successHandler;

    @Autowired
    public SecurityConfig(SuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
                                                            ServerLogoutSuccessHandler handler) {
        http
                .csrf().disable()
                .cors().disable()
                    .authorizeExchange()
                    .pathMatchers("/actuator/**")
                    .permitAll()
                .and()
                    .authorizeExchange()
                    .anyExchange()
                    .authenticated()
                .and()
                    .oauth2Login()
                    //.authenticationSuccessHandler(successHandler)
                .and()
                    .logout();
                    //.logoutSuccessHandler(handler);

        return http.build();
    }

    @Bean
    public ServerLogoutSuccessHandler keycloakLogoutSuccessHandler(@Autowired ReactiveClientRegistrationRepository repository) {

        OidcClientInitiatedServerLogoutSuccessHandler oidcLogoutSuccessHandler =
                new OidcClientInitiatedServerLogoutSuccessHandler(repository);

        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("http://localhost:8080/api/user/home");

        return oidcLogoutSuccessHandler;
    }
}
