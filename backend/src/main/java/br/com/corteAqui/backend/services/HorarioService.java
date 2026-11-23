package br.com.corteAqui.backend.services;

import br.com.corteAqui.backend.dtos.request.HorarioEdicaoRequestDTO;
import br.com.corteAqui.backend.dtos.request.HorarioRequestDTO;
import br.com.corteAqui.backend.dtos.response.HorarioResponseDTO;
import br.com.corteAqui.backend.enums.StatusHorario;
import br.com.corteAqui.backend.exceptions.*;
import br.com.corteAqui.backend.models.Barbeiro;
import br.com.corteAqui.backend.models.Horario;
import br.com.corteAqui.backend.repositories.AgendamentoRepository;
import br.com.corteAqui.backend.repositories.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.corteAqui.backend.enums.StatusAgendamento;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;
    @Autowired
    private AgendamentoRepository agendamentoRepository;
    @Autowired
    private BarbeiroService barbeiroService;

    public void cadastrar(HorarioRequestDTO dados, Long idUsuarioLogado) {
        Barbeiro barbeiroBuscado = barbeiroService.encontrarBarbeiroPorUsuarioLogado(idUsuarioLogado);

        boolean duplicado =
                horarioRepository.existsByBarbeiroIdAndDataHorarioAndHora(
                        barbeiroBuscado.getId(),
                        dados.dataHorario(),
                        dados.hora()
                );

        if (duplicado) {
            throw new ExecaoInformacoesDuplicadas("Já existe um horário cadastrado nesse horário", "Informações duplicadas");
        }

        boolean horaEAntesDaHoraAtual = verificarSeHoraEAntesDaHoraAtual(dados.dataHorario(), dados.hora());

        if(horaEAntesDaHoraAtual) {
            throw new ExcecaoHoraInvalida("A hora não pode ser anterior a atual", "Error: Hora anterior a atual!");
        }

        Horario horario = new Horario();
        horario.setBarbeiro(barbeiroBuscado);
        horario.setDataHorario(dados.dataHorario());
        horario.setHora(dados.hora());
        horario.setStatusHorario(StatusHorario.DISPONIVEL);

        Horario salvo = horarioRepository.save(horario);
    }

    public List<HorarioResponseDTO> listarPorBarbeiro(Long idUsuarioLogado) {

        Barbeiro barbeiroBuscado = barbeiroService.encontrarBarbeiroPorUsuarioLogado(idUsuarioLogado);

        if(barbeiroBuscado == null) {
            throw new RecursoNaoEncontradoExcecao("Barbeiro não encontrado", "Recurso não encontrado");
        }

        List<HorarioResponseDTO> todosHorarios =  horarioRepository.findByBarbeiroIdAndStatusHorarioNot(
                        barbeiroBuscado.getId(),
                        StatusHorario.REMOVIDO
                )
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

        if(todosHorarios.isEmpty()) {
            throw new ExcecaoListaVazia("Não há horarios cadastrados", "Lista de recursos vazia");
        }

        return todosHorarios;
    }

    public List<HorarioResponseDTO> listarDisponiveis(Long barbeiroId) {
    
        if (!barbeiroRepository.existsById(barbeiroId)) {
            throw new RecursoNaoEncontradoExcecao("Barbeiro não encontrado.", "Error: Recurso não encontrado.");
        }

        LocalDateTime agora = LocalDateTime.now();

        return horarioRepository.buscarDisponiveis(
                    barbeiroId,
                    LocalDate.now(),
                    agora,
                    StatusHorario.DISPONIVEL,
                    List.of(StatusAgendamento.AGENDADO, StatusAgendamento.CONCLUIDO)
        )
            .stream()
            .map(this::converterParaDTO)
            .toList();
    }

    public void editar(Long horarioId, Long idUsuarioLogado, HorarioEdicaoRequestDTO dados) {
      
        Barbeiro barbeiroBuscado = barbeiroService.encontrarBarbeiroPorUsuarioLogado(idUsuarioLogado);
      
        Horario horario = buscarPorId(horarioId, barbeiroBuscado.getUsuario().getId());


        LocalDate dataFinal = dados.dataHorario() != null
                ? dados.dataHorario()
                : horario.getDataHorario();

        LocalTime horaFinal = dados.hora() != null
                ? dados.hora()
                : horario.getHora();


        boolean duplicado =
                horarioRepository.existsByBarbeiroIdAndDataHorarioAndHora(
                        barbeiroBuscado.getId(),
                        dataFinal,
                        horaFinal
                );

        if (duplicado) {
            throw new ExecaoInformacoesDuplicadas("Já existe horário para esse barbeiro nessa data e hora.", "Informações duplicadas");
        }

        boolean horaEAntesDaHoraAtual = verificarSeHoraEAntesDaHoraAtual(dados.dataHorario(), dados.hora());

        if(horaEAntesDaHoraAtual) {
            throw new ExcecaoHoraInvalida("A hora não pode ser anterior a atual!", "Error: Hora invalida");
        }

        boolean possuiAgendamento = agendamentoRepository.existsByHorarioId(horarioId);

        horario.setDataHorario(dataFinal);
        horario.setHora(horaFinal);

        horarioRepository.save(horario);

        if (possuiAgendamento) {
            System.out.println("Horário alterado com sucesso. O cliente deverá ser notificado para decidir se deseja manter ou cancelar o agendamento.");
        }
    }

    public void excluir(Long horarioId, Long idUsuarioLogado) {
        Horario horario = buscarPorId(horarioId, idUsuarioLogado);

        if (agendamentoRepository.existsByHorarioId(horarioId)) {
            System.out.println("Horário removido. O cliente deverá ser notificado para escolher outro horário.");
        }

        horarioRepository.delete(horario);
    }

    private Horario buscarPorId(Long horarioId, Long idUsuarioLogado) {

        Barbeiro barbeirobuscado = barbeiroService.encontrarBarbeiroPorUsuarioLogado(idUsuarioLogado);

        Horario horarioBuscado = horarioRepository.findByIdAndBarbeiroId(horarioId, barbeirobuscado.getId()).orElseThrow(() -> new RecursoNaoEncontradoExcecao(
                "Horário não encontrado para esse barbeiro.", "Recurso não encontrado"
        ));

        return horarioBuscado;
    }

    private HorarioResponseDTO converterParaDTO(Horario horario) {

        return new HorarioResponseDTO(
                horario.getId(),
                horario.getDataHorario(),
                horario.getHora(),
                horario.getStatusHorario(),
                horario.getBarbeiro().getId()
        );
    }


    /**
     * @Description: Esse metodo irá verificar se um hora é anterior a hora atual do dia atual.
     * Exemplo:
     * Data Atual - 2026-02-28
     * Hora Atual - 08:00
     *
     * Data De Entrada: 2026-02-28
     * Hora De Entrada: 07:00
     *
     * O metodo irá retornar falso. Assim permitindo ser cadastrados horarios corretamente.
     * @param dataHora
     * @param hora
     * @return boolean
     */
    private boolean verificarSeHoraEAntesDaHoraAtual(LocalDate dataHora, LocalTime hora) {
      
        if(LocalDate.now().isEqual(dataHora) && hora.isBefore(LocalTime.now().truncatedTo(ChronoUnit.MINUTES))) return true;

        return false;
    }

}
