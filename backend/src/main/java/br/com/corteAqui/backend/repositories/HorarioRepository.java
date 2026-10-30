package br.com.corteAqui.backend.repositories;

import br.com.corteAqui.backend.models.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.corteAqui.backend.enums.StatusHorario;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

    List<Horario> findByBarbeiroId(Long barbeiroId);

    Optional<Horario> findByIdAndBarbeiroId(Long id, Long barbeiroId);

    boolean existsByBarbeiroIdAndDataHorarioAndHora(
            Long barbeiroId,
            LocalDate dataHorario,
            Time hora
    );

    List<Horario> findByBarbeiroIdAndStatusHorarioNot(
            Long barbeiroId,
            StatusHorario statusHorario
    );
}