package com.scuola.gestione_corsi.dto;

import com.scuola.gestione_corsi.model.Ruolo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * DTO per l'entità Utente.
 * Utilizzato per il trasferimento dei dati degli utenti tra i layer dell'applicazione.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtenteDTO {
    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private Ruolo ruolo;
} 