package com.renzo.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

//@Configuration
public class SecurityConfig {

//    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        http
//                .csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .authorizeExchange(exchange -> exchange
//                        .pathMatchers("/auth/**").permitAll()  // allow login requests
//                        .anyExchange().authenticated()         // all other endpoints must be secured
//                );
////                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
//
//        return http.build();
//    }
}