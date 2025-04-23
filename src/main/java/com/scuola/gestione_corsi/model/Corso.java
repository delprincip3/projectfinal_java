package com.scuola.gestione_corsi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Entità che rappresenta un corso offerto dalla scuola.
 * Contiene le informazioni sul corso e le relazioni con docenti e studenti.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "corsi")
public class Corso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 1000)
    private String descrizione;

    @Column(nullable = false)
    private Integer durata; // in ore

    @Column(nullable = false)
    private Integer maxStudenti;

    @Column(nullable = false)
    private Double prezzo;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToMany
    @JoinTable(
        name = "corso_docente",
        joinColumns = @JoinColumn(name = "corso_id"),
        inverseJoinColumns = @JoinColumn(name = "docente_id")
    )
    private List<Docente> docenti;

    @OneToMany(mappedBy = "corso", cascade = CascadeType.ALL)
    private List<Iscrizione> iscrizioni;

    @OneToMany(mappedBy = "corso", cascade = CascadeType.ALL)
    private List<Lezione> lezioni;
} 