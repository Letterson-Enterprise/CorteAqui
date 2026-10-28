package br.com.corteAqui.backend.repositories;

import br.com.corteAqui.backend.models.Barbeiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BarbeiroRepository extends JpaRepository<Barbeiro, Long> {

    Optional<Barbeiro> findByUsuarioId(Long usuarioId);
}