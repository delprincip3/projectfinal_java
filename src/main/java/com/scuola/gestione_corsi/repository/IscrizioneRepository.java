package com.scuola.gestione_corsi.repository;

import com.scuola.gestione_corsi.model.Iscrizione;
import com.scuola.gestione_corsi.model.StatoIscrizione;
import com.scuola.gestione_corsi.model.Studente;
import com.scuola.gestione_corsi.model.Corso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository per l'entità Iscrizione.
 * Fornisce metodi per l'accesso ai dati delle iscrizioni.
 */
@Repository
public interface IscrizioneRepository extends JpaRepository<Iscrizione, Long> {
    
    /**
     * Trova tutte le iscrizioni di uno studente.
     * @param studenteId L'ID dello studente
     * @return Lista di iscrizioni dello studente
     */
    List<Iscrizione> findByStudente_Id(Long studenteId);
    
    /**
     * Trova tutte le iscrizioni a un corso.
     * @param corsoId L'ID del corso
     * @return Lista di iscrizioni al corso
     */
    List<Iscrizione> findByCorso_Id(Long corsoId);
    
    /**
     * Trova le iscrizioni per stato.
     * @param stato Lo stato delle iscrizioni da cercare
     * @return Lista di iscrizioni con lo stato specificato
     */
    List<Iscrizione> findByStato(StatoIscrizione stato);
    
    /**
     * Trova le iscrizioni di uno studente a un corso specifico.
     * @param studenteId L'ID dello studente
     * @param corsoId L'ID del corso
     * @return Lista di iscrizioni dello studente al corso
     */
    List<Iscrizione> findByStudente_IdAndCorso_Id(Long studenteId, Long corsoId);

    boolean existsByStudenteAndCorso(Studente studente, Corso corso);
    Optional<Iscrizione> findByStudenteAndCorso(Studente studente, Corso corso);
} 