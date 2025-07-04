package com.renzo.auth_service.controller;

import com.renzo.auth_service.dto.AuthRequest;
import com.renzo.auth_service.dto.AuthResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private static final String SECRET = "myjwtsecret";

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        if ("user".equals(authRequest.getUsername()) && "password".equals(authRequest.getPassword())) {
            String token = Jwts.builder()
                    .setSubject(authRequest.getUsername())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 3600_000)) // 1 hour
                    .signWith(SignatureAlgorithm.HS256, SECRET)
                    .compact();
            return ResponseEntity.ok(new AuthResponse(token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
