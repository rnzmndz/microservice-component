package com.renzo.api_gateway.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

//    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
//    private String jwtIssuerUri;

//    @Bean
//    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
//        http
//                .authorizeExchange(exchanges -> exchanges
//                        .pathMatchers("/public/**").permitAll()
//                        .anyExchange().authenticated()
//                )
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
//        return http.build();
//    }


//    @Bean
//    public ReactiveJwtDecoder jwtDecoder() {
//        // Use ReactiveJwtDecoders for reactive applications
//        return ReactiveJwtDecoders.fromIssuerLocation(jwtIssuerUri);
//    }
}
