package com.scuola.gestione_corsi.repository;

import com.scuola.gestione_corsi.model.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository per l'entità Aula.
 * Fornisce metodi per l'accesso ai dati delle aule.
 */
@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {
    
    /**
     * Trova tutte le aule ordinate per nome.
     * @return Lista di aule ordinata
     */
    List<Aula> findAllByOrderByNomeAsc();
    
    /**
     * Trova le aule con capienza maggiore o uguale a un valore specifico.
     * @param capienza La capienza minima
     * @return Lista di aule con capienza sufficiente
     */
    List<Aula> findByCapienzaGreaterThanEqual(Integer capienza);
    
    /**
     * Trova un'aula per nome.
     * @param nome Il nome dell'aula
     * @return L'aula se trovata
     */
    Optional<Aula> findByNome(String nome);

    boolean existsByNome(String nome);
} 