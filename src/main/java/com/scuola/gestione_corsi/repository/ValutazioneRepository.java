package com.scuola.gestione_corsi.repository;

import com.scuola.gestione_corsi.model.Valutazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository per l'entità Valutazione.
 * Fornisce metodi per l'accesso ai dati delle valutazioni.
 */
@Repository
public interface ValutazioneRepository extends JpaRepository<Valutazione, Long> {
    
    /**
     * Trova tutte le valutazioni di un'iscrizione.
     * @param iscrizioneId L'ID dell'iscrizione
     * @return Lista di valutazioni dell'iscrizione
     */
    List<Valutazione> findByIscrizione_Id(Long iscrizioneId);
    
    /**
     * Trova le valutazioni con voto superiore a un valore specifico.
     * @param voto Il voto minimo
     * @return Lista di valutazioni con voto superiore
     */
    List<Valutazione> findByVotoGreaterThan(Integer voto);
    
    /**
     * Trova le valutazioni con voto inferiore a un valore specifico.
     * @param voto Il voto massimo
     * @return Lista di valutazioni con voto inferiore
     */
    List<Valutazione> findByVotoLessThan(Integer voto);
} 