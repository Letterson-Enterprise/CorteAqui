package br.com.corteAqui.backend.controllers;

import br.com.corteAqui.backend.dtos.request.ClienteRequestAtualizacaoDTO;
import br.com.corteAqui.backend.dtos.request.ClienteRequestDTO;
import br.com.corteAqui.backend.dtos.request.UsuarioRequestAtualizacaoDTO;
import br.com.corteAqui.backend.models.Cliente;
import br.com.corteAqui.backend.models.Usuario;
import br.com.corteAqui.backend.services.ClienteService;
import br.com.corteAqui.backend.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<String> cadastrarCliente(@RequestBody ClienteRequestDTO corpoCliente) {
        clienteService.cadastrarCliente(corpoCliente);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario cadastrado com sucesso.");
    }

    @PutMapping("/editar/informacoes/cliente")
    public ResponseEntity<String> atualizarCliente(@AuthenticationPrincipal Usuario usuario, @RequestBody ClienteRequestAtualizacaoDTO corpoCliente) {
        clienteService.atualizarCliente(usuario.getId(), corpoCliente);
        return ResponseEntity.ok("Cliente atualizado com sucesso.");
    }

    @PutMapping("/editar/informacoes/usuario")
    public ResponseEntity<String> editarInformacoesUsuario(@AuthenticationPrincipal Usuario usuario, @RequestBody UsuarioRequestAtualizacaoDTO corpoUsuario) {
        usuarioService.atualizarUsuario(usuario.getId(), corpoUsuario.nomeUsuario());
        return ResponseEntity.ok("Usuário atualizado com sucesso.");
    }
}
