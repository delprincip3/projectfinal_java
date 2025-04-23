package com.scuola.gestione_corsi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

/**
 * DTO per l'entità Pagamento.
 * Utilizzato per il trasferimento dei dati dei pagamenti tra i layer dell'applicazione.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoDTO {
    private Long id;
    private Double importo;
    private LocalDate dataPagamento;
    private String metodoPagamento;
    private Long iscrizioneId;
} 