package com.scuola.gestione_corsi.repository;

import com.scuola.gestione_corsi.model.MaterialeDidattico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository per l'entità MaterialeDidattico.
 * Fornisce metodi per l'accesso ai dati dei materiali didattici.
 */
@Repository
public interface MaterialeDidatticoRepository extends JpaRepository<MaterialeDidattico, Long> {
    
    /**
     * Trova tutti i materiali didattici di una lezione.
     * @param lezioneId L'ID della lezione
     * @return Lista di materiali didattici della lezione
     */
    List<MaterialeDidattico> findByLezione_Id(Long lezioneId);
    
    /**
     * Trova i materiali didattici per tipo.
     * @param tipo Il tipo di materiale
     * @return Lista di materiali didattici del tipo specificato
     */
    List<MaterialeDidattico> findByTipo(String tipo);
    
    /**
     * Trova i materiali didattici che contengono una parola nel titolo.
     * @param titolo La parola da cercare nel titolo
     * @return Lista di materiali didattici che contengono la parola nel titolo
     */
    List<MaterialeDidattico> findByTitoloContaining(String titolo);
} 