package com.scuola.gestione_corsi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Entità che rappresenta una categoria di corsi.
 * Permette di organizzare i corsi per area tematica.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categorie")
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false, length = 1000)
    private String descrizione;

    @Column(nullable = false)
    private String icona;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<Corso> corsi;
} 