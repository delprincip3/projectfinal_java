package com.scuola.gestione_corsi.controller;

import com.scuola.gestione_corsi.dto.AuthenticationRequest;
import com.scuola.gestione_corsi.dto.AuthenticationResponse;
import com.scuola.gestione_corsi.dto.RegisterRequest;
import com.scuola.gestione_corsi.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticazione", description = "API per l'autenticazione e la registrazione")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(summary = "Registra un nuovo utente")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    @Operation(summary = "Autentica un utente esistente")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
} 