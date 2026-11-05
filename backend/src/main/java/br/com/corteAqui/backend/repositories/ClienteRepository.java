package br.com.corteAqui.backend.repositories;

import br.com.corteAqui.backend.models.Cliente;
import br.com.corteAqui.backend.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByNome(String nome);
    boolean existsBySobreNome(String sobreNome);

    @Query("SELECT Cliente FROM Cliente c JOIN Usuario u ON c.usuario.id = u.id WHERE c.id = ?1")
    Optional<Cliente> encontrarClientePeloUsuario(Long id);
}

