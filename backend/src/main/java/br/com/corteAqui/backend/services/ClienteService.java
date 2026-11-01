package br.com.corteAqui.backend.services;

import br.com.corteAqui.backend.dtos.request.ClienteRequestDTO;
import br.com.corteAqui.backend.models.Cliente;
import br.com.corteAqui.backend.models.Usuario;
import br.com.corteAqui.backend.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
