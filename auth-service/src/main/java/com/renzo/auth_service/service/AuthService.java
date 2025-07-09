package com.renzo.auth_service.service;

import com.renzo.auth_service.client.EmployeeClient;
import com.renzo.auth_service.dto.RegisterRequest;
import com.renzo.auth_service.dto.RegisterResponse;
import com.renzo.auth_service.model.AuthUser;
import com.renzo.auth_service.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeClient employeeClient;

    public RegisterResponse register(RegisterRequest request) {
        if (authUserRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email already exists");
        }

        UUID userId = UUID.randomUUID();
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        AuthUser authUser = new AuthUser(
                userId,
                request.getEmail(),
                hashedPassword,
                request.getRole() != null ? request.getRole() : "USER"
        );

        authUserRepository.save(authUser);

        request.getEmployeeCreateDto().setId(userId);

        // TODO: Call user service to create user profile
        // userClient.createUser(new UserCreateRequest(userId, request.getFullName(), request.getEmail()));
        employeeClient.createEmployee(request.getEmployeeCreateDto());

        return new RegisterResponse(userId, "User registered successfully");
    }
}
