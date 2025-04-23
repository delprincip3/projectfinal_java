package com.scuola.gestione_corsi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO per l'entità Corso.
 * Utilizzato per il trasferimento dei dati dei corsi tra i layer dell'applicazione.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorsoDTO {
    private Long id;
    private String nome;
    private String descrizione;
    private Integer durata;
    private Integer maxStudenti;
    private Double prezzo;
    private Long categoriaId;
} 