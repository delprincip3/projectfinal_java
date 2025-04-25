package com.scuola.gestione_corsi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO per l'entità Lezione.
 * Utilizzato per il trasferimento dei dati delle lezioni tra i layer dell'applicazione.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LezioneDTO {
    private Long id;
    private String titolo;
    private String descrizione;
    private LocalDateTime dataOra;
    private Integer durata; // in minuti
    private Long corsoId;
    private Long docenteId;
    private Long aulaId;
} 