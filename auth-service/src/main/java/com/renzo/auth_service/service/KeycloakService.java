package com.renzo.auth_service.service;

import com.renzo.auth_service.dto.RegisterRequest;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KeycloakService {

    @Value("${keycloak.server-uri}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.username}")
    private String username;

    @Value("${keycloak.password}")
    private String password;

    private Keycloak keycloak;

    @PostConstruct
    public void init() {
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm("master") // admin realm
                .clientId("admin-cli")
                .username(username)  // Keycloak admin username
                .password(password)  // Keycloak admin password
                .build();
    }

    public String createUser(RegisterRequest request) {
        UserRepresentation userRep = new UserRepresentation();
        userRep.setUsername(request.getUsername());
        userRep.setEmail(request.getEmail());
        userRep.setEnabled(true);
        userRep.setEmailVerified(false);

        // Add required attributes
        userRep.singleAttribute("origin", "spring-gateway");

        Response response = keycloak.realm(realm).users().create(userRep);

        if (response.getStatus() != 201) {
            String errorMsg = response.readEntity(String.class);
            throw new RuntimeException("Failed to create user: " + response.getStatus() + " - " + errorMsg);
        }

        String userId = CreatedResponseUtil.getCreatedId(response);

        // Set password
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(request.getPassword());

        keycloak.realm(realm)
                .users()
                .get(userId)
                .resetPassword(passwordCred);

        return userId;
    }
}