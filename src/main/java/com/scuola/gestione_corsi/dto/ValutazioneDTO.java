package com.scuola.gestione_corsi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

/**
 * DTO per l'entità Valutazione.
 * Utilizzato per il trasferimento dei dati delle valutazioni tra i layer dell'applicazione.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValutazioneDTO {
    private Long id;
    private Integer voto;
    private String commento;
    private LocalDate dataValutazione;
    private Long iscrizioneId;
} 