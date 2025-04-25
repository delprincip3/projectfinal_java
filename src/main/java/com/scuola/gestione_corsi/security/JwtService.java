package com.scuola.gestione_corsi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {

    @Value("${spring.security.jwt.secret}")
    private String secretKey;

    @Value("${spring.security.jwt.expiration}")
    private long jwtExpiration;

    public String extractUsername(String token) {
        try {
            log.debug("Estrazione username dal token");
            return extractClaim(token, Claims::getSubject);
        } catch (Exception e) {
            log.error("Errore nell'estrazione dell'username dal token: {}", e.getMessage());
            return null;
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        } catch (Exception e) {
            log.error("Errore nell'estrazione del claim dal token: {}", e.getMessage());
            return null;
        }
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        log.debug("Generazione token con ruolo: {}", role);
        claims.put("role", role);
        return generateToken(claims, userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        log.debug("Generazione token per l'utente: {}", userDetails.getUsername());
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            boolean isUsernameValid = username != null && username.equals(userDetails.getUsername());
            boolean isTokenExpired = isTokenExpired(token);
            
            log.debug("Validazione token per l'utente: {}", username);
            log.debug("Username valido: {}", isUsernameValid);
            log.debug("Token scaduto: {}", isTokenExpired);
            
            return isUsernameValid && !isTokenExpired;
        } catch (Exception e) {
            log.error("Errore nella validazione del token: {}", e.getMessage());
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            log.error("Errore nel controllo della scadenza del token: {}", e.getMessage());
            return true;
        }
    }

    private Date extractExpiration(String token) {
        try {
            return extractClaim(token, Claims::getExpiration);
        } catch (Exception e) {
            log.error("Errore nell'estrazione della data di scadenza del token: {}", e.getMessage());
            return new Date(0);
        }
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("Errore nell'estrazione dei claims dal token: {}", e.getMessage());
            throw e;
        }
    }

    private Key getSignInKey() {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            log.error("Errore nella generazione della chiave di firma: {}", e.getMessage());
            throw e;
        }
    }
} 