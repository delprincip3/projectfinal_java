package com.scuola.gestione_corsi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Entità che rappresenta un docente della scuola.
 * Contiene le informazioni professionali e i corsi insegnati.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "docenti")
public class Docente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false)
    private String specializzazione;

    @Column(nullable = false)
    private String cv;

    @Column(nullable = false)
    private Double tariffa;

    @OneToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    @ManyToMany(mappedBy = "docenti")
    private List<Corso> corsi;

    @OneToMany(mappedBy = "docente", cascade = CascadeType.ALL)
    private List<Lezione> lezioni;
} 