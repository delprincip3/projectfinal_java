package com.scuola.gestione_corsi.repository;

import com.scuola.gestione_corsi.model.Corso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository per l'entità Corso.
 * Fornisce metodi per l'accesso ai dati dei corsi.
 */
@Repository
public interface CorsoRepository extends JpaRepository<Corso, Long> {
    
    /**
     * Trova tutti i corsi ordinati per nome.
     * @return Lista di corsi ordinata
     */
    List<Corso> findAllByOrderByNomeAsc();
    
    /**
     * Trova i corsi di una specifica categoria.
     * @param categoriaId L'ID della categoria
     * @return Lista di corsi della categoria
     */
    List<Corso> findByCategoria_Id(Long categoriaId);
    
    /**
     * Trova i corsi che non hanno raggiunto il numero massimo di studenti.
     * @return Lista di corsi con posti disponibili
     */
    List<Corso> findByMaxStudentiGreaterThan(Integer count);
    
    /**
     * Trova i corsi per intervallo di prezzo.
     * @param prezzoMin Prezzo minimo
     * @param prezzoMax Prezzo massimo
     * @return Lista di corsi nel range di prezzo specificato
     */
    List<Corso> findByPrezzoBetween(Double prezzoMin, Double prezzoMax);

    List<Corso> findByDocenti_Id(Long docenteId);

    boolean existsByNome(String nome);
    Optional<Corso> findByNome(String nome);
} 