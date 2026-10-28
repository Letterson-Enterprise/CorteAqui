package br.com.corteAqui.backend.repositories;

import br.com.corteAqui.backend.models.Papel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PapelRepository extends JpaRepository<Papel, Long> {
}
