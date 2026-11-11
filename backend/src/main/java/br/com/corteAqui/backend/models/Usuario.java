package br.com.corteAqui.backend.models;

import jakarta.persistence.*;
import lombok.Data;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tb_usuarios")
@Data
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80, unique = true)
    private String nomeUsuario;

    @Column(nullable = false, unique = true)
    private String senha;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
    private List<Email> emails = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "idPapel", nullable = false)
    private Papel papel;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(papel);
    }

    @Override
    public @Nullable String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return nomeUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
