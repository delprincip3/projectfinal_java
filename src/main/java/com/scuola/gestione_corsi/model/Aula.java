package com.scuola.gestione_corsi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Entità che rappresenta un'aula della scuola.
 * Contiene le informazioni sulla capienza e le lezioni programmate.
 */
@Entity
@Table(name = "aule")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Aula {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private Integer capienza;

    @OneToMany(mappedBy = "aula", cascade = CascadeType.ALL)
    private List<Lezione> lezioni;
} 