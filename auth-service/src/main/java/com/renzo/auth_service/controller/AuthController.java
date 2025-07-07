package com.renzo.auth_service.controller;

import com.renzo.auth_service.dto.AuthRequest;
import com.renzo.auth_service.dto.RegisterRequest;
import com.renzo.auth_service.dto.RegisterResponse;
import com.renzo.auth_service.dto.TokenResponse;
import com.renzo.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Value("${keycloak.token-uri}")
    private String tokenUri;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    private final WebClient webClient = WebClient.create();

    @PostMapping("/login")
    public Mono<TokenResponse> login(@RequestBody AuthRequest request) {
        return webClient.post()
                .uri(tokenUri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue("grant_type=password&client_id=" + clientId +
                        "&client_secret=" + clientSecret +
                        "&username=" + request.getUsername() +
                        "&password=" + request.getPassword())
                .retrieve()
                .bodyToMono(TokenResponse.class);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        RegisterResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }
}
