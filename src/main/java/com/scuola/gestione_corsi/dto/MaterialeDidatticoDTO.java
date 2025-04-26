package com.scuola.gestione_corsi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank(message = "Il titolo è obbligatorio")
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-_,;:()]+$", message = "Il titolo può contenere solo lettere, numeri, spazi e i seguenti caratteri speciali: -_,;:()")
    private String titolo;

    @NotBlank(message = "Il tipo è obbligatorio")
    @Pattern(regexp = "^(PDF|VIDEO|DOCUMENTO|PRESENTAZIONE)$", message = "Il tipo deve essere uno dei seguenti: PDF, VIDEO, DOCUMENTO, PRESENTAZIONE")
    private String tipo;

    @NotBlank(message = "L'URL è obbligatorio")
    @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", message = "L'URL deve essere un indirizzo valido")
    private String url;

    @NotNull(message = "L'ID della lezione è obbligatorio")
    private Long lezioneId;
} 