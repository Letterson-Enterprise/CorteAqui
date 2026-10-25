package br.com.corteAqui.backend.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_emails")
@Data
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private boolean statusAtivo;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;
}
