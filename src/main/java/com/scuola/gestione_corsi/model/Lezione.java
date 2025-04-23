package com.scuola.gestione_corsi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entità che rappresenta una lezione di un corso.
 * Contiene le informazioni sulla lezione e i materiali didattici associati.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lezioni")
public class Lezione {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titolo;

    @Column(nullable = false, length = 1000)
    private String descrizione;

    @Column(nullable = false)
    private LocalDateTime dataOra;

    @Column(nullable = false)
    private Integer durata; // in minuti

    @ManyToOne
    @JoinColumn(name = "corso_id", nullable = false)
    private Corso corso;

    @ManyToOne
    @JoinColumn(name = "docente_id", nullable = false)
    private Docente docente;

    @ManyToOne
    @JoinColumn(name = "aula_id", nullable = false)
    private Aula aula;

    @OneToMany(mappedBy = "lezione", cascade = CascadeType.ALL)
    private List<MaterialeDidattico> materiali;

    @OneToMany(mappedBy = "lezione")
    private List<Presenza> presenze;
} 