package com.scuola.gestione_corsi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Entità che rappresenta un pagamento per un'iscrizione.
 * Contiene le informazioni sul pagamento e il metodo utilizzato.
 */
@Entity
@Table(name = "pagamenti")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double importo;

    @Column(nullable = false)
    private LocalDate dataPagamento;

    @Column(nullable = false)
    private String metodoPagamento;

    @ManyToOne
    @JoinColumn(name = "iscrizione_id", nullable = false)
    private Iscrizione iscrizione;
} 