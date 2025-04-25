package com.scuola.gestione_corsi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.Authentication;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userEmail;

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.debug("Nessun token JWT trovato nell'header per la richiesta: {}", request.getRequestURI());
                filterChain.doFilter(request, response);
                return;
            }

            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt);
            
            if (userEmail == null) {
                log.warn("Impossibile estrarre l'email dal token per la richiesta: {}", request.getRequestURI());
                filterChain.doFilter(request, response);
                return;
            }
            
            log.debug("Token JWT estratto per l'utente: {} per la richiesta: {}", userEmail, request.getRequestURI());

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                log.debug("UserDetails caricati: {} con ruoli: {}", userDetails.getUsername(), userDetails.getAuthorities());
                
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    log.debug("Token valido per l'utente: {}", userEmail);
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    // Imposta l'autenticazione nel SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.debug("Autenticazione impostata nel SecurityContext per l'utente: {}", userEmail);
                    
                    // Verifica che l'autenticazione sia stata impostata correttamente
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    if (authentication != null && authentication.isAuthenticated()) {
                        log.debug("Autenticazione verificata nel SecurityContext: {}", authentication);
                    } else {
                        log.error("Autenticazione non impostata correttamente nel SecurityContext");
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Errore di autenticazione");
                        return;
                    }
                } else {
                    log.warn("Token non valido per l'utente: {}", userEmail);
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token non valido");
                    return;
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Errore durante l'autenticazione per la richiesta {}: {}", request.getRequestURI(), e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Errore di autenticazione");
        }
    }
} 