package br.com.corteAqui.backend.repositories;

import br.com.corteAqui.backend.models.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.corteAqui.backend.enums.StatusHorario;
import br.com.corteAqui.backend.enums.StatusAgendamento;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

    Optional<Horario> findByIdAndBarbeiroId(Long id, Long barbeiroId);

    boolean existsByBarbeiroIdAndDataHorarioAndHora(
            Long barbeiroId,
            LocalDate dataHorario,
            LocalTime hora
    );

    List<Horario> findByBarbeiroIdAndStatusHorarioNot(
            Long barbeiroId,
            StatusHorario statusHorario
    );

   @Query("""
        select h
        from Horario h
        where h.barbeiro.id = :barbeiroId
          and h.statusHorario = :statusDisponivel
          and (h.dataHorario > :hoje
               or (h.dataHorario = :hoje and h.hora >= :horaAtual))
          and not exists (
              select 1
              from Agendamento a
              where a.horario.id = h.id
                and a.statusAgendamento in :statusOcupados
          )
        order by h.dataHorario, h.hora, h.id
        """)
List<Horario> buscarDisponiveis(
        @Param("barbeiroId") Long barbeiroId,
        @Param("hoje") LocalDate hoje,
        @Param("horaAtual") LocalTime horaAtual,
        @Param("statusDisponivel") StatusHorario statusDisponivel,
        @Param("statusOcupados") List<StatusAgendamento> statusOcupados
);
}
