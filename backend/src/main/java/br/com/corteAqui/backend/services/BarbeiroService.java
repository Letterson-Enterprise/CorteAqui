package br.com.corteAqui.backend.services;

import br.com.corteAqui.backend.dtos.request.BarbeiroRequestDTO;
import br.com.corteAqui.backend.dtos.response.BarbeiroResponseDTO;
import br.com.corteAqui.backend.exceptions.RecursoNaoEncontradoExcecao;
import br.com.corteAqui.backend.models.Barbeiro;
import br.com.corteAqui.backend.models.Usuario;
import br.com.corteAqui.backend.repositories.BarbeiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BarbeiroService {

    @Autowired
    private BarbeiroRepository barbeiroRepository;

    @Autowired
    private UsuarioService usuarioService;

    public void cadastrarBarbeiro(BarbeiroRequestDTO barbeiro) {
        Usuario novoUsuariobarbeiro = usuarioService.cadastrarUsuario(barbeiro.nomeUsuario(), barbeiro.senha(), barbeiro.email(), Long.valueOf(2));

        Barbeiro novobarbeiro = new Barbeiro();
        novobarbeiro.setNome(barbeiro.nome());
        novobarbeiro.setSobreNome(barbeiro.sobreNome());
        novobarbeiro.setCpf(barbeiro.cpf());
        novobarbeiro.setUsuario(novoUsuariobarbeiro);
        barbeiroRepository.save(novobarbeiro);
    }

    public BarbeiroResponseDTO converterEmBarbeiroResponseDTO(Barbeiro barbeiro) {
        return new BarbeiroResponseDTO(barbeiro.getNome(), barbeiro.getSobreNome(), barbeiro.getCpf());
    }

    public Barbeiro encontrarBarbeiroPorUsuarioLogado(Long idUsuarioLogado) {
        return barbeiroRepository.findByUsuarioId(idUsuarioLogado).orElseThrow(() -> new RecursoNaoEncontradoExcecao("Barbeiro não encontrado", "Recurso não encontrado"));
    }
}
