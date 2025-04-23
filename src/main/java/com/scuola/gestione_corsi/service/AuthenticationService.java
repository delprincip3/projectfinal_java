package com.scuola.gestione_corsi.service;

import com.scuola.gestione_corsi.dto.AuthenticationRequest;
import com.scuola.gestione_corsi.dto.AuthenticationResponse;
import com.scuola.gestione_corsi.dto.RegisterRequest;
import com.scuola.gestione_corsi.model.Ruolo;
import com.scuola.gestione_corsi.model.Utente;
import com.scuola.gestione_corsi.repository.UtenteRepository;
import com.scuola.gestione_corsi.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UtenteRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = Utente.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .ruolo(Ruolo.valueOf(request.getRuolo()))
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
} 