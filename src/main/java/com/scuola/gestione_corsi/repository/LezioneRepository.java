package com.scuola.gestione_corsi.repository;

import com.scuola.gestione_corsi.model.Lezione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository per l'entità Lezione.
 * Fornisce metodi per l'accesso ai dati delle lezioni.
 */
@Repository
public interface LezioneRepository extends JpaRepository<Lezione, Long> {
    
    /**
     * Trova tutte le lezioni di un corso.
     * @param corsoId L'ID del corso
     * @return Lista di lezioni del corso
     */
    List<Lezione> findByCorsoId(Long corsoId);
    
    /**
     * Trova tutte le lezioni di un docente.
     * @param docenteId L'ID del docente
     * @return Lista di lezioni del docente
     */
    List<Lezione> findByDocenteId(Long docenteId);
    
    /**
     * Trova le lezioni in un'aula specifica.
     * @param aulaId L'ID dell'aula
     * @return Lista di lezioni nell'aula
     */
    List<Lezione> findByAulaId(Long aulaId);
    
    /**
     * Trova le lezioni in un intervallo di date.
     * @param dataInizio Data di inizio
     * @param dataFine Data di fine
     * @return Lista di lezioni nell'intervallo specificato
     */
    List<Lezione> findByDataOraBetween(LocalDateTime dataInizio, LocalDateTime dataFine);

    boolean existsByTitolo(String titolo);
    Optional<Lezione> findByTitolo(String titolo);
} 