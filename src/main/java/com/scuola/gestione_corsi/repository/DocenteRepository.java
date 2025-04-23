package com.scuola.gestione_corsi.repository;

import com.scuola.gestione_corsi.model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository per l'entità Docente.
 * Fornisce metodi per l'accesso ai dati dei docenti.
 */
@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {
    
    /**
     * Trova tutti i docenti ordinati per cognome e nome.
     * @return Lista di docenti ordinata
     */
    List<Docente> findAllByOrderByCognomeAscNomeAsc();
    
    /**
     * Trova i docenti che insegnano in un corso specifico.
     * @param corsoId L'ID del corso
     * @return Lista di docenti del corso
     */
    List<Docente> findByCorsi_Id(Long corsoId);
    
    /**
     * Trova i docenti per specializzazione.
     * @param specializzazione La specializzazione da cercare
     * @return Lista di docenti con la specializzazione specificata
     */
    List<Docente> findBySpecializzazione(String specializzazione);
} 