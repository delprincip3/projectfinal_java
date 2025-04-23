package com.scuola.gestione_corsi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Entità che rappresenta una valutazione di uno studente per un corso.
 * Contiene il voto e i commenti del docente.
 */
@Entity
@Table(name = "valutazioni")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Valutazione {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer voto;

    @Column(length = 1000)
    private String commento;

    @Column(nullable = false)
    private LocalDate dataValutazione;

    @ManyToOne
    @JoinColumn(name = "iscrizione_id", nullable = false)
    private Iscrizione iscrizione;
} 