package com.scuola.gestione_corsi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Entità che rappresenta uno studente iscritto ai corsi.
 * Contiene le informazioni personali e le iscrizioni ai corsi.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "studenti")
public class Studente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false)
    private LocalDate dataNascita;

    @Column(nullable = false)
    private String indirizzo;

    @Column(nullable = false)
    private String telefono;

    @OneToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    @OneToMany(mappedBy = "studente", cascade = CascadeType.ALL)
    private List<Iscrizione> iscrizioni;
} 