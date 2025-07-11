package com.renzo.auth_service.service;

import com.renzo.auth_service.client.EmployeeClient;
import com.renzo.auth_service.dto.RegisterRequest;
import com.renzo.auth_service.dto.RegisterResponse;
import com.renzo.auth_service.model.AuthUser;
import com.renzo.auth_service.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthUserRepository authUserRepository;
    private final KeycloakService keycloakService;
    private final EmployeeClient employeeClient;

    @Value("${keycloak.realm}")
    private String realm;

    public RegisterResponse register(RegisterRequest request) {
        if (authUserRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        UUID userId = UUID.randomUUID();

        String keycloakId = keycloakService.createUser(request);

        AuthUser authUser = AuthUser.builder()
                .id(UUID.fromString(keycloakId))
                .realmId(realm)
                .username(request.getUsername())
                .email(request.getEmail())
                .build();

        request.getEmployeeCreateDto().setId(userId);

        // TODO: Call user service to create user profile
        // userClient.createUser(new UserCreateRequest(userId, request.getFullName(), request.getEmail()));
        employeeClient.createEmployee(request.getEmployeeCreateDto());

        authUserRepository.save(authUser);

        return new RegisterResponse(userId, "User registered successfully");
    }
}
