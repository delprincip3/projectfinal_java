package com.scuola.gestione_corsi.dto;

import com.scuola.gestione_corsi.model.StatoIscrizione;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO per l'entità Iscrizione.
 * Utilizzato per il trasferimento dei dati delle iscrizioni tra i layer dell'applicazione.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IscrizioneDTO {
    private Long id;
    private LocalDateTime dataIscrizione;
    private StatoIscrizione stato;
    private String metodoPagamento;
    private Long studenteId;
    private Long corsoId;
} 