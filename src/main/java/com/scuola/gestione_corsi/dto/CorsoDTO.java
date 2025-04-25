package com.scuola.gestione_corsi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO per l'entità Corso.
 * Utilizzato per il trasferimento dei dati dei corsi tra i layer dell'applicazione.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CorsoDTO {
    private Long id;
    private String nome;
    private String descrizione;
    private Integer durata;
    private Integer maxStudenti;
    private Double prezzo;
    private Long categoriaId;
    private List<Long> docenti;
} 