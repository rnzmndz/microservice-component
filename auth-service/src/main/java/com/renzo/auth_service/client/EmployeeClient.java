package com.renzo.auth_service.client;

import com.renzo.auth_service.dto.employee.employee.EmployeeCreateDto;
import com.renzo.auth_service.dto.employee.employee.EmployeeResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "employee-service", url = "http://localhost:8081/api/v1/employees")
public interface EmployeeClient {

    @PostMapping
    ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeCreateDto employeeUpdateDto);
}
