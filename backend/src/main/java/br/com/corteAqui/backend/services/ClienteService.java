package br.com.corteAqui.backend.services;

import br.com.corteAqui.backend.dtos.request.ClienteRequestAtualizacaoDTO;
import br.com.corteAqui.backend.dtos.request.ClienteRequestDTO;
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
        Usuario novoUsuarioCliente = usuarioService.cadastrarUsuario(cliente.nomeUsuario(), cliente.senha(), cliente.email(), Long.valueOf(1));

        Cliente novoCliente = new Cliente();
        novoCliente.setNome(cliente.nome());
        novoCliente.setSobreNome(cliente.sobreNome());
        novoCliente.setUsuario(novoUsuarioCliente);
        clienteRepository.save(novoCliente);
    }

    @Transactional
    public void atualizarCliente(Long id, ClienteRequestAtualizacaoDTO cliente) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado no sistema."));

        if (cliente.nome() == null || cliente.sobreNome() == null || id == null) {
            throw new IllegalStateException("O cliente selecionado não possui um usuário válido vinculado.");
        }

        clienteExistente.setNome(cliente.nome());
        clienteExistente.setSobreNome(cliente.sobreNome());

        clienteRepository.save(clienteExistente);
    }
}
