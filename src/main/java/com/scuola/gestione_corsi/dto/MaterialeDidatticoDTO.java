package com.scuola.gestione_corsi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO per l'entità MaterialeDidattico.
 * Utilizzato per il trasferimento dei dati dei materiali didattici tra i layer dell'applicazione.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialeDidatticoDTO {
    private Long id;
    private String titolo;
    private String tipo;
    private String url;
    private Long lezioneId;
} 