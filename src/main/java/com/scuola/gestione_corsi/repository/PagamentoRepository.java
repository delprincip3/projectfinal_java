package com.scuola.gestione_corsi.repository;

import com.scuola.gestione_corsi.model.Iscrizione;
import com.scuola.gestione_corsi.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository per l'entità Pagamento.
 * Fornisce metodi per l'accesso ai dati dei pagamenti.
 */
@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    
    /**
     * Trova tutti i pagamenti di un'iscrizione.
     * @param iscrizioneId L'ID dell'iscrizione
     * @return Lista di pagamenti dell'iscrizione
     */
    List<Pagamento> findByIscrizione_Id(Long iscrizioneId);
    
    /**
     * Trova i pagamenti effettuati in un intervallo di date.
     * @param dataInizio Data di inizio
     * @param dataFine Data di fine
     * @return Lista di pagamenti nell'intervallo specificato
     */
    List<Pagamento> findByDataPagamentoBetween(LocalDateTime dataInizio, LocalDateTime dataFine);
    
    /**
     * Trova i pagamenti effettuati con un metodo specifico.
     * @param metodoPagamento Il metodo di pagamento
     * @return Lista di pagamenti effettuati con il metodo specificato
     */
    List<Pagamento> findByMetodoPagamento(String metodoPagamento);
    
    /**
     * Trova i pagamenti con importo maggiore di un valore specifico.
     * @param importo L'importo minimo
     * @return Lista di pagamenti con importo superiore
     */
    List<Pagamento> findByImportoGreaterThan(Double importo);

    boolean existsByIscrizione(Iscrizione iscrizione);
    Optional<Pagamento> findByIscrizione(Iscrizione iscrizione);
} 