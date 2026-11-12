package br.com.corteAqui.backend.services;

import br.com.corteAqui.backend.dtos.request.HorarioEdicaoRequestDTO;
import br.com.corteAqui.backend.dtos.request.HorarioRequestDTO;
import br.com.corteAqui.backend.dtos.response.HorarioResponseDTO;
import br.com.corteAqui.backend.enums.StatusHorario;
import br.com.corteAqui.backend.exceptions.*;
import br.com.corteAqui.backend.models.Barbeiro;
import br.com.corteAqui.backend.models.Horario;
import br.com.corteAqui.backend.repositories.AgendamentoRepository;
import br.com.corteAqui.backend.repositories.BarbeiroRepository;
import br.com.corteAqui.backend.repositories.HorarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class HorarioService {

    private final HorarioRepository horarioRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final BarbeiroRepository barbeiroRepository;

    public HorarioResponseDTO cadastrar(HorarioRequestDTO dados) {

        if (dados.barbeiroId() == null) {
            throw new DadosInvalidosException(
                    "Informe o ID do barbeiro."
            );
        }

        if (dados.dataHorario() == null || dados.hora() == null) {
            throw new DataHoraInvalidaException(
                    "Informe a data e a hora do horário."
            );
        }

        Barbeiro barbeiro = barbeiroRepository.findById(dados.barbeiroId())
                .orElseThrow(() -> new BarbeiroNaoEncontradoException(
                        "Barbeiro não encontrado."
                ));

        Time hora = Time.valueOf(dados.hora());

        validarDataHora(dados.dataHorario(), hora);

        boolean duplicado =
                horarioRepository.existsByBarbeiroIdAndDataHorarioAndHoraAndStatusHorarioNot(
                        dados.barbeiroId(),
                        dados.dataHorario(),
                        hora,
                        StatusHorario.REMOVIDO
                );

        if (duplicado) {
            throw new HorarioDuplicadoException(
                    "Já existe um horário para esse barbeiro nessa data e hora."
            );
        }

        Horario horario = new Horario();
        horario.setBarbeiro(barbeiro);
        horario.setDataHorario(dados.dataHorario());
        horario.setHora(hora);
        horario.setStatusHorario(StatusHorario.DISPONIVEL);

        Horario salvo = horarioRepository.save(horario);

        return converterParaDTO(salvo);
    }

    public List<HorarioResponseDTO> listarPorBarbeiro(Long barbeiroId) {

        barbeiroRepository.findById(barbeiroId)
                .orElseThrow(() -> new BarbeiroNaoEncontradoException(
                        "Barbeiro não encontrado."
                ));

        return horarioRepository.findByBarbeiroIdAndStatusHorarioNot(
                        barbeiroId,
                        StatusHorario.REMOVIDO
                )
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public String editar(
            Long horarioId,
            Long barbeiroId,
            HorarioEdicaoRequestDTO dados
    ) {
        Horario horario = buscarPorId(horarioId, barbeiroId);

        if (horario.getStatusHorario() == StatusHorario.REMOVIDO) {
            throw new HorarioNaoEncontradoException(
                    "Esse horário já foi removido."
            );
        }

        LocalDate dataFinal = dados.dataHorario() != null
                ? dados.dataHorario()
                : horario.getDataHorario();

        Time horaFinal = dados.hora() != null
                ? Time.valueOf(dados.hora())
                : horario.getHora();

        validarDataHora(dataFinal, horaFinal);

        boolean duplicado =
                horarioRepository.existsByBarbeiroIdAndDataHorarioAndHoraAndStatusHorarioNot(
                        barbeiroId,
                        dataFinal,
                        horaFinal,
                        StatusHorario.REMOVIDO
                );

        if (duplicado
                && (!dataFinal.equals(horario.getDataHorario())
                || !horaFinal.equals(horario.getHora()))) {
            throw new HorarioDuplicadoException(
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
            throw new HorarioNaoEncontradoException(
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

    private Horario buscarPorId(Long horarioId, Long barbeiroId) {

        return horarioRepository.findByIdAndBarbeiroId(
                horarioId,
                barbeiroId
        ).orElseThrow(() -> new HorarioNaoEncontradoException(
                "Horário não encontrado para esse barbeiro."
        ));
    }

    private HorarioResponseDTO converterParaDTO(Horario horario) {

        return new HorarioResponseDTO(
                horario.getId(),
                horario.getDataHorario(),
                horario.getHora().toLocalTime(),
                horario.getStatusHorario(),
                horario.getBarbeiro().getId()
        );
    }

    private void validarDataHora(LocalDate data, Time hora) {

        if (data == null || hora == null) {
            throw new DataHoraInvalidaException(
                    "A data e a hora são obrigatórias."
            );
        }

        LocalDate hoje = LocalDate.now();
        LocalTime agora = LocalTime.now();

        if (data.isBefore(hoje)) {
            throw new DataHoraInvalidaException(
                    "A data do horário não pode estar no passado."
            );
        }

        if (data.isEqual(hoje)
                && hora.toLocalTime().isBefore(agora)) {
            throw new DataHoraInvalidaException(
                    "A hora do horário não pode estar no passado."
            );
        }
    }
}
