package com.scuola.gestione_corsi.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entità che rappresenta un materiale didattico associato a una lezione.
 * Contiene le informazioni sul materiale e il suo URL di accesso.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "materiali_didattici")
public class MaterialeDidattico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titolo;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "lezione_id", nullable = false)
    private Lezione lezione;
} 