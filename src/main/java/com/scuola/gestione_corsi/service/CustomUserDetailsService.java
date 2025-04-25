package com.scuola.gestione_corsi.service;

import com.scuola.gestione_corsi.model.Utente;
import com.scuola.gestione_corsi.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UtenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug("Caricamento UserDetails per email: {}", email);
        Utente utente = utenteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato con email: " + email));

        String authority = "ROLE_" + utente.getRuolo().name();
        log.debug("Autorizzazione creata: {}", authority);

        return new User(
                utente.getEmail(),
                utente.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(authority))
        );
    }
} 