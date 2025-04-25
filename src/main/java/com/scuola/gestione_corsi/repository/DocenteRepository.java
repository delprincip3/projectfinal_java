package com.scuola.gestione_corsi.repository;

import com.scuola.gestione_corsi.model.Docente;
import com.scuola.gestione_corsi.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    @Query("SELECT DISTINCT d FROM Docente d LEFT JOIN FETCH d.corsi")
    List<Docente> findAll();
    
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

    /**
     * Verifica se esiste un docente associato a un utente.
     * @param utente L'utente da cercare
     * @return true se esiste un docente associato all'utente, false altrimenti
     */
    boolean existsByUtente(Utente utente);

    /**
     * Trova un docente associato a un utente.
     * @param utente L'utente da cercare
     * @return Il docente associato all'utente, se esiste
     */
    Optional<Docente> findByUtente(Utente utente);

    @Query("SELECT d FROM Docente d LEFT JOIN FETCH d.corsi WHERE d.id = :id")
    Optional<Docente> findByIdWithCorsi(@Param("id") Long id);
} 