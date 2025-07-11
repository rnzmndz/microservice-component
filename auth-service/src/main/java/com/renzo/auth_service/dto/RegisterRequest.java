package com.renzo.auth_service.dto;

import com.renzo.auth_service.dto.employee.employee.EmployeeCreateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RegisterRequest {

    private String username;
    private String email;
    private String password;
    private String role;
    private EmployeeCreateDto employeeCreateDto;
}
