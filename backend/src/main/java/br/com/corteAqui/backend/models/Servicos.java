package br.com.corteAqui.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_servicos")
public class Servicos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeServico;

    @Column(nullable = false)
    private double preco;

    @ManyToOne
    @JoinColumn(name = "idCategoria",nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "idBarbeiro", nullable = false)
    private Barbeiro barbeiro;
}
