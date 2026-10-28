package br.com.corteAqui.backend.repositories;

import br.com.corteAqui.backend.models.Servicos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<Servicos, Long> {
}
