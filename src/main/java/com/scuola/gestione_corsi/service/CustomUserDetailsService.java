package com.scuola.gestione_corsi.service;

import com.scuola.gestione_corsi.model.Utente;
import com.scuola.gestione_corsi.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UtenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utente utente = utenteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato con email: " + email));

        return new User(
                utente.getEmail(),
                utente.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + utente.getRuolo().name()))
        );
    }
} 