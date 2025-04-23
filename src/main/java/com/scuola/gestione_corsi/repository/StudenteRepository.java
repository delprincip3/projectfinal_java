package com.scuola.gestione_corsi.repository;

import com.scuola.gestione_corsi.model.Studente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository per l'entità Studente.
 * Fornisce metodi per l'accesso ai dati degli studenti.
 */
@Repository
public interface StudenteRepository extends JpaRepository<Studente, Long> {
    
    /**
     * Trova tutti gli studenti ordinati per cognome e nome.
     * @return Lista di studenti ordinata
     */
    List<Studente> findAllByOrderByCognomeAscNomeAsc();
    
    /**
     * Trova gli studenti che hanno un'iscrizione attiva a un corso.
     * @param corsoId L'ID del corso
     * @return Lista di studenti iscritti al corso
     */
    List<Studente> findByIscrizioni_Corso_Id(Long corsoId);
} 