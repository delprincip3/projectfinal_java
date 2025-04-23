package com.scuola.gestione_corsi.repository;

import com.scuola.gestione_corsi.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository per l'entità Categoria.
 * Fornisce metodi per l'accesso ai dati delle categorie.
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    /**
     * Trova tutte le categorie ordinate per nome.
     * @return Lista di categorie ordinata
     */
    List<Categoria> findAllByOrderByNomeAsc();
    
    /**
     * Trova una categoria per nome.
     * @param nome Il nome della categoria
     * @return La categoria se trovata
     */
    Categoria findByNome(String nome);
} 