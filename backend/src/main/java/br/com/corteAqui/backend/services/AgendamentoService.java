package br.com.corteAqui.backend.services;

import br.com.corteAqui.backend.dtos.request.AgendamentoRequestDTO;
import br.com.corteAqui.backend.enums.StatusAgendamento;
import br.com.corteAqui.backend.exceptions.ExecaoInformacoesDuplicadas;
import br.com.corteAqui.backend.models.Agendamento;
import br.com.corteAqui.backend.models.Cliente;
import br.com.corteAqui.backend.models.Horario;
import br.com.corteAqui.backend.models.Servicos;
import br.com.corteAqui.backend.repositories.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.corteAqui.backend.exceptions.ErroExcecao;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private ServicosService servicosService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private HorarioService horarioService;


    public void realizarAgendament(AgendamentoRequestDTO corpoAgendamento, Long idClienteLogado, Long idHorario) {
        Agendamento agendamento = new Agendamento();

        Cliente clienteLogado = clienteService.buscarClientePorUsuarioLogado(idClienteLogado);

        Servicos servicoEscolhido = servicosService.buscarServicoPorId(corpoAgendamento.idServico());

        Horario horarioEscolhido = horarioService.buscarPorId(idHorario);

        agendamento.setCliente(clienteLogado);
        agendamento.setHorario(horarioEscolhido);
        agendamento.setServicos(servicoEscolhido);
        agendamento.setStatusAgendamento(StatusAgendamento.AGENDADO);

        if(agendamentoRepository.existsByClienteIdAndStatusAgendamento(clienteLogado.getId(),StatusAgendamento.AGENDADO)){
            throw new ExecaoInformacoesDuplicadas("Você já possui um agendamento marcado!", "ERRO: Informações duplicadas");
        }

        try {
            agendamentoRepository.save(agendamento);
        } catch (Exception e) {
            throw new ErroExcecao("Error interno: " + e.getMessage(), "Erro Interno!");
        }
    }

}
