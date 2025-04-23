package com.scuola.gestione_corsi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

/**
 * DTO per l'entità Studente.
 * Utilizzato per il trasferimento dei dati degli studenti tra i layer dell'applicazione.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudenteDTO {
    private Long id;
    private String nome;
    private String cognome;
    private LocalDate dataNascita;
    private String indirizzo;
    private String telefono;
    private Long utenteId;
} 