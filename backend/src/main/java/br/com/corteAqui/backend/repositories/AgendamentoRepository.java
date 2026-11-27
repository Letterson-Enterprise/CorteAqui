package br.com.corteAqui.backend.repositories;

import br.com.corteAqui.backend.models.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    boolean existsByHorarioId(Long horarioId);
}