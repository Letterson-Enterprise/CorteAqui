package br.com.corteAqui.backend.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_tokens")
@Data
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;
}
