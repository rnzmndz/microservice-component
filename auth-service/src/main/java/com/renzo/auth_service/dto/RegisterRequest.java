package com.renzo.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RegisterRequest {

    private String email;
    private String password;
    private String fullName;
    private String role;
}
