package com.scuola.gestione_corsi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entità che rappresenta l'iscrizione di uno studente a un corso.
 * Contiene le informazioni sull'iscrizione e le relative valutazioni.
 */
@Entity
@Table(name = "iscrizioni", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"studente_id", "corso_id"})
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Iscrizione {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataIscrizione;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoIscrizione stato;

    @Column(nullable = false)
    private String metodoPagamento;

    @ManyToOne
    @JoinColumn(name = "studente_id", nullable = false)
    private Studente studente;

    @ManyToOne
    @JoinColumn(name = "corso_id", nullable = false)
    private Corso corso;

    @OneToMany(mappedBy = "iscrizione", cascade = CascadeType.ALL)
    private List<Valutazione> valutazioni;

    @OneToMany(mappedBy = "iscrizione", cascade = CascadeType.ALL)
    private List<Pagamento> pagamenti;

    @OneToMany(mappedBy = "iscrizione", cascade = CascadeType.ALL)
    private List<Presenza> presenze;
} 