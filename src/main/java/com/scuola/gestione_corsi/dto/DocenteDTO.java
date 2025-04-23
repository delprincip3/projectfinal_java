package com.scuola.gestione_corsi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO per l'entità Docente.
 * Utilizzato per il trasferimento dei dati dei docenti tra i layer dell'applicazione.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocenteDTO {
    private Long id;
    private String nome;
    private String cognome;
    private String specializzazione;
    private String cv;
    private Double tariffa;
    private Long utenteId;
} 