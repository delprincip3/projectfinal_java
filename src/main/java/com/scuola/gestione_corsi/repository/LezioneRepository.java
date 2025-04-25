package com.scuola.gestione_corsi.repository;

import com.scuola.gestione_corsi.model.Lezione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END " +
           "FROM lezioni l " +
           "WHERE l.aula_id = :aulaId " +
           "AND l.id != :excludeId " +
           "AND ((l.data_ora BETWEEN :inizio AND :fine) " +
           "OR (l.data_ora + make_interval(mins => l.durata) BETWEEN :inizio AND :fine) " +
           "OR (:inizio BETWEEN l.data_ora AND l.data_ora + make_interval(mins => l.durata)))", 
           nativeQuery = true)
    boolean existsByAulaIdAndDataOraBetween(
        @Param("aulaId") Long aulaId,
        @Param("inizio") LocalDateTime inizio,
        @Param("fine") LocalDateTime fine,
        @Param("excludeId") Long excludeId
    );
    
    @Query(value = "SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END " +
           "FROM lezioni l " +
           "WHERE l.docente_id = :docenteId " +
           "AND l.id != :excludeId " +
           "AND ((l.data_ora BETWEEN :inizio AND :fine) " +
           "OR (l.data_ora + make_interval(mins => l.durata) BETWEEN :inizio AND :fine) " +
           "OR (:inizio BETWEEN l.data_ora AND l.data_ora + make_interval(mins => l.durata)))", 
           nativeQuery = true)
    boolean existsByDocenteIdAndDataOraBetween(
        @Param("docenteId") Long docenteId,
        @Param("inizio") LocalDateTime inizio,
        @Param("fine") LocalDateTime fine,
        @Param("excludeId") Long excludeId
    );
} 