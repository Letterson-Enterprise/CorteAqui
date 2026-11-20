package br.com.corteAqui.backend.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.engine.internal.Cascade;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_categorias")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @OneToMany(mappedBy = "categoria", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Servicos> servicos = new ArrayList<>();
}
