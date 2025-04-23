package com.scuola.gestione_corsi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

/**
 * DTO per l'entità Presenza.
 * Utilizzato per il trasferimento dei dati delle presenze tra i layer dell'applicazione.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PresenzaDTO {
    private Long id;
    private LocalDate dataPresenza;
    private Boolean presente;
    private Long iscrizioneId;
} 