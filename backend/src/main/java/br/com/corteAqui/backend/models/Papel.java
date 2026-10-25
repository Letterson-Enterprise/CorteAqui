package br.com.corteAqui.backend.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_papeis")
@Data
public class Papel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String descricaoPapel;
}
