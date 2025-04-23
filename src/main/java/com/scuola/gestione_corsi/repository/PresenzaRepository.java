package com.scuola.gestione_corsi.repository;

import com.scuola.gestione_corsi.model.Presenza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository per l'entità Presenza.
 * Fornisce metodi per l'accesso ai dati delle presenze.
 */
@Repository
public interface PresenzaRepository extends JpaRepository<Presenza, Long> {
    
    /**
     * Trova tutte le presenze di un'iscrizione.
     * @param iscrizioneId L'ID dell'iscrizione
     * @return Lista di presenze dell'iscrizione
     */
    List<Presenza> findByIscrizione_Id(Long iscrizioneId);
    
    /**
     * Trova le presenze in una data specifica.
     * @param dataPresenza La data da cercare
     * @return Lista di presenze nella data specificata
     */
    List<Presenza> findByDataPresenza(LocalDate dataPresenza);
    
    /**
     * Trova le presenze in un intervallo di date.
     * @param dataInizio Data di inizio
     * @param dataFine Data di fine
     * @return Lista di presenze nell'intervallo specificato
     */
    List<Presenza> findByDataPresenzaBetween(LocalDate dataInizio, LocalDate dataFine);
    
    /**
     * Trova le presenze per stato (presente/assente).
     * @param presente true per le presenze, false per le assenze
     * @return Lista di presenze con lo stato specificato
     */
    List<Presenza> findByPresente(Boolean presente);
} 