
package br.com.corteAqui.backend.services;

import br.com.corteAqui.backend.enums.StatusHorario;
import br.com.corteAqui.backend.models.Horario;
import br.com.corteAqui.backend.repositories.AgendamentoRepository;
import br.com.corteAqui.backend.repositories.HorarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class HorarioService {

    private final HorarioRepository horarioRepository;
    private final AgendamentoRepository agendamentoRepository;

    public HorarioService(
            HorarioRepository horarioRepository,
            AgendamentoRepository agendamentoRepository
    ) {
        this.horarioRepository = horarioRepository;
        this.agendamentoRepository = agendamentoRepository;
    }

    public Horario cadastrar(Horario horario) {
        validarDataHora(horario.getDataHorario(), horario.getHora());

        if (horario.getBarbeiro() == null
                || horario.getBarbeiro().getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O barbeiro deve ser informado."
            );
        }

        boolean duplicado =
                horarioRepository.existsByBarbeiroIdAndDataHorarioAndHora(
                        horario.getBarbeiro().getId(),
                        horario.getDataHorario(),
                        horario.getHora()
                );

        if (duplicado) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe um horário para esse barbeiro nessa data e hora."
            );
        }

        horario.setStatusHorario(StatusHorario.DISPONIVEL);

        return horarioRepository.save(horario);
    }

    public List<Horario> listarPorBarbeiro(Long barbeiroId) {
        return horarioRepository.findByBarbeiroIdAndStatusHorarioNot(
                barbeiroId,
                StatusHorario.REMOVIDO
        );
    }

    public Horario buscarPorId(Long horarioId, Long barbeiroId) {
        return horarioRepository.findByIdAndBarbeiroId(
                horarioId,
                barbeiroId
        ).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Horário não encontrado para esse barbeiro."
        ));
    }

    public String editar(
            Long horarioId,
            Long barbeiroId,
            LocalDate novaData,
            Time novaHora
    ) {
        Horario horario = buscarPorId(horarioId, barbeiroId);

        if (horario.getStatusHorario() == StatusHorario.REMOVIDO) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Não é possível editar um horário removido."
            );
        }

        LocalDate dataFinal = novaData != null
                ? novaData
                : horario.getDataHorario();

        Time horaFinal = novaHora != null
                ? novaHora
                : horario.getHora();

        validarDataHora(dataFinal, horaFinal);

        boolean duplicado =
                horarioRepository.existsByBarbeiroIdAndDataHorarioAndHora(
                        barbeiroId,
                        dataFinal,
                        horaFinal
                );

        if (duplicado
                && (!dataFinal.equals(horario.getDataHorario())
                || !horaFinal.equals(horario.getHora()))) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe outro horário para esse barbeiro nessa data e hora."
            );
        }

        boolean possuiAgendamento =
                agendamentoRepository.existsByHorarioId(horarioId);

        horario.setDataHorario(dataFinal);
        horario.setHora(horaFinal);

        horarioRepository.save(horario);

        if (possuiAgendamento) {
            return "Horário alterado com sucesso. O cliente deverá ser notificado para decidir se deseja manter ou cancelar o agendamento.";
        }

        return "Horário alterado com sucesso.";
    }

    public String excluir(Long horarioId, Long barbeiroId) {
        Horario horario = buscarPorId(horarioId, barbeiroId);

        if (horario.getStatusHorario() == StatusHorario.REMOVIDO) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse horário já foi removido."
            );
        }

        if (agendamentoRepository.existsByHorarioId(horarioId)) {
            horario.setStatusHorario(StatusHorario.REMOVIDO);
            horarioRepository.save(horario);

            return "Horário removido. O cliente deverá ser notificado para escolher outro horário.";
        }

        horarioRepository.delete(horario);

        return "Horário excluído com sucesso.";
    }

    private void validarDataHora(LocalDate data, Time hora) {
        if (data == null || hora == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A data e a hora são obrigatórias."
            );
        }

        LocalDate hoje = LocalDate.now();
        LocalTime agora = LocalTime.now();

        if (data.isBefore(hoje)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A data do horário não pode estar no passado."
            );
        }

        if (data.isEqual(hoje)
                && hora.toLocalTime().isBefore(agora)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A hora do horário não pode estar no passado."
            );
        }
    }
}
