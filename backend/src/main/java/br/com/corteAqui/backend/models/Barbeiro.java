package br.com.corteAqui.backend.models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_barbeiros")
@Data
public class Barbeiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String sobreNome;

    @Column(nullable = false, unique = true,length = 14)
    private String cpf;

    @OneToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "barbeiro")
    private List<Servicos> servicos = new ArrayList<>();

    @OneToMany(mappedBy = "barbeiro")
    private List<Horario> horarios = new ArrayList<>();

}
