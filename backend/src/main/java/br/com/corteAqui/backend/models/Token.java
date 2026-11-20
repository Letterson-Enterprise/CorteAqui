package br.com.corteAqui.backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_tokens")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
