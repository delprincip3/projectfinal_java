package com.scuola.gestione_corsi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Entità che rappresenta la presenza di uno studente a una lezione.
 * Contiene le informazioni sulla presenza e la data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "presenze")
public class Presenza {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataPresenza;

    @Column(nullable = false)
    private Boolean presente;

    @ManyToOne
    @JoinColumn(name = "iscrizione_id", nullable = false)
    private Iscrizione iscrizione;

    @ManyToOne
    private Lezione lezione;
} 