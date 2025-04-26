package com.scuola.gestione_corsi.repository;

import com.scuola.gestione_corsi.model.Iscrizione;
import com.scuola.gestione_corsi.model.Valutazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
     * Trova la valutazione più recente di un'iscrizione.
     * @param iscrizione L'iscrizione
     * @return La valutazione più recente, se esiste
     */
    Optional<Valutazione> findFirstByIscrizioneOrderByDataValutazioneDesc(Iscrizione iscrizione);
    
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

    boolean existsByIscrizione(Iscrizione iscrizione);
} 