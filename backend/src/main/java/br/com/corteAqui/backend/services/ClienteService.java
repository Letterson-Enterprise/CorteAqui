package br.com.corteAqui.backend.services;

import br.com.corteAqui.backend.dtos.request.ClienteRequestAtualizacaoDTO;
import br.com.corteAqui.backend.dtos.request.ClienteRequestDTO;
import br.com.corteAqui.backend.exceptions.ExcecaoCamposInvalidos;
import br.com.corteAqui.backend.exceptions.ExecaoInformacoesDuplicadas;
import br.com.corteAqui.backend.exceptions.RecursoNaoEncontradoExcecao;
import br.com.corteAqui.backend.models.Cliente;
import br.com.corteAqui.backend.models.Usuario;
import br.com.corteAqui.backend.repositories.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ClienteRepository clienteRepository;


    public void cadastrarCliente(ClienteRequestDTO cliente) {
        Usuario novoUsuarioCliente = usuarioService.cadastrarUsuario(cliente.nomeUsuario(), cliente.senha(), cliente.email(), 1L);

        Cliente novoCliente = new Cliente();
        novoCliente.setNome(cliente.nome());
        novoCliente.setSobreNome(cliente.sobreNome());
        novoCliente.setUsuario(novoUsuarioCliente);
        clienteRepository.save(novoCliente);
    }

    @Transactional
    public void atualizarCliente(Long id, ClienteRequestAtualizacaoDTO cliente) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoExcecao("Cliente não encontrado no sistema.", "ERROR"));

        clienteExistente.setNome(cliente.nome());
        clienteExistente.setSobreNome(cliente.sobreNome());

        clienteRepository.save(clienteExistente);
    }
}
