package com.scuola.gestione_corsi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO per l'entità Docente.
 * Utilizzato per il trasferimento dei dati dei docenti tra i layer dell'applicazione.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocenteDTO {
    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private String specializzazione;
    private String cv;
    private Double tariffa;
    private Long utenteId;
    private List<Long> corsi;
} 