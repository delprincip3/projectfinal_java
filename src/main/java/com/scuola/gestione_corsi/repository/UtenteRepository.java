package com.scuola.gestione_corsi.repository;

import com.scuola.gestione_corsi.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository per l'entità Utente.
 * Fornisce metodi per l'accesso ai dati degli utenti.
 */
@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
    
    /**
     * Trova un utente tramite email.
     * @param email L'email dell'utente da cercare
     * @return Un Optional contenente l'utente se trovato
     */
    Optional<Utente> findByEmail(String email);
    
    /**
     * Verifica se esiste un utente con l'email specificata.
     * @param email L'email da verificare
     * @return true se esiste un utente con quell'email, false altrimenti
     */
    boolean existsByEmail(String email);
} 