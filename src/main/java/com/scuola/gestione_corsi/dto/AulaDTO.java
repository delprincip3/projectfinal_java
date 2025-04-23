package com.scuola.gestione_corsi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO per l'entità Aula.
 * Utilizzato per il trasferimento dei dati delle aule tra i layer dell'applicazione.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AulaDTO {
    private Long id;
    private String nome;
    private Integer capienza;
} 