package br.com.corteAqui.backend.services;

import br.com.corteAqui.backend.dtos.request.ServicoRequestDTO;
import br.com.corteAqui.backend.dtos.response.ServicoResponseDTO;
import br.com.corteAqui.backend.exceptions.*;
import br.com.corteAqui.backend.models.Barbeiro;
import br.com.corteAqui.backend.models.Categoria;
import br.com.corteAqui.backend.models.Servicos;
import br.com.corteAqui.backend.models.Usuario;
import br.com.corteAqui.backend.repositories.BarbeiroRepository;
import br.com.corteAqui.backend.repositories.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicosService {

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private BarbeiroService barbeiroService;

    @Autowired
    private CategoriaService categoriaService;

    public void cadastrarServico(ServicoRequestDTO novoServico, Long idUsuarioLogado) {
        if(novoServico.nomeServico().isBlank()) {
            throw new ExcecaoCamposInvalidos("O campo nome deve ser preenchido", "Campo invalido");
        }

        if(novoServico.preco() < 0  || novoServico.idCategoria() < 0) {
            throw new ExececaoNumerosInvalidos("Números negativos não são permitidos", "Números invalidso");
        }

        boolean servicoExistente = servicoRepository.existsByNomeServico(novoServico.nomeServico());

        if(servicoExistente) {
            throw new ExecaoInformacoesDuplicadas("Já existe um serviço com o mesmo nome", "Informações duplicadas");
        }

        Categoria categoriaBuscada = categoriaService.buscarCategoriaPorId(novoServico.idCategoria());

        Barbeiro barbeiroBuscado = barbeiroService.encontrarBarbeiroPorUsuarioLogado(idUsuarioLogado);

        Servicos servico = new Servicos();

        servico.setNomeServico(novoServico.nomeServico());
        servico.setCategoria(categoriaBuscada);
        servico.setBarbeiro(barbeiroBuscado);
        servico.setPreco(novoServico.preco());

        servicoRepository.save(servico);
    }


    @Transactional(readOnly = true)
    public List<ServicoResponseDTO> listarTodosServicos(Long id) {
        List<ServicoResponseDTO> todosServicos = servicoRepository.listaDeServicosBarbeiro(id).stream().map(this::converterEmServicoResponseDTO).toList();

        if(todosServicos.isEmpty()) {
            throw new ExcecaoListaVazia("Não há serviços cadastrados", "Lista vazia");
        }

        return todosServicos;
    }

    public ServicoResponseDTO converterEmServicoResponseDTO(Servicos servico) {
        return new ServicoResponseDTO(servico.getId(), servico.getNomeServico(), servico.getPreco());
    }

    public Servicos buscarServicoPorId(Long id, Long idBarbeiro) {
        if(id <= 0) {
            throw new ExececaoNumerosInvalidos("Números negativos não são permitidos", "Números invalidso");
        }

        Servicos servicoBuscado = servicoRepository.encontrarServicoPorIdEBarbeiro(id, idBarbeiro).orElseThrow(() -> new RecursoNaoEncontradoExcecao("Serviço não encontrado", "Recurso não encontrado"));

        return servicoBuscado;
    }

    public ServicoResponseDTO atualizarServico(ServicoRequestDTO novosDadosServico, Long id, Long idUsuarioLogado) {
        Servicos servicoBuscado = buscarServicoPorId(id, idUsuarioLogado);

        Servicos servicoExistente = servicoRepository.verficarSeNomeExisteEmOutraLinha(novosDadosServico.nomeServico(), servicoBuscado.getId());

        servicoBuscado.setPreco(novosDadosServico.preco());

        servicoBuscado.setNomeServico(novosDadosServico.nomeServico());

        Categoria categoriaBuscada = categoriaService.buscarCategoriaPorId(novosDadosServico.idCategoria());

        Barbeiro barbeiroBuscado = barbeiroService.encontrarBarbeiroPorUsuarioLogado(idUsuarioLogado);

        servicoBuscado.setBarbeiro(barbeiroBuscado);
        servicoBuscado.setCategoria(categoriaBuscada);

        Servicos servicoAtualizado = servicoRepository.save(servicoBuscado);

        return converterEmServicoResponseDTO(servicoAtualizado);
    }

    @Transactional
    public void deletarServico(Long id, Long idUsuarioLogado) {
        Servicos servicoBuscado = buscarServicoPorId(id, idUsuarioLogado);

        servicoRepository.delete(servicoBuscado);
    }

    public ServicoResponseDTO visualizarServico(Long id, Long idUsuarioLogado) {
        Servicos servicoBuscado = buscarServicoPorId(id, idUsuarioLogado);
        return converterEmServicoResponseDTO(servicoBuscado);
    }
}
