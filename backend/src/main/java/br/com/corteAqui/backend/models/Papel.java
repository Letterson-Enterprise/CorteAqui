package br.com.corteAqui.backend.models;

import jakarta.persistence.*;
import lombok.Data;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "tb_papeis")
@Data
public class Papel implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String descricaoPapel;

    @Override
    public @Nullable String getAuthority() {
        return descricaoPapel;
    }
}
