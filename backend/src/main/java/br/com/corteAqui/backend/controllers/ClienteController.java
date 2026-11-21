package br.com.corteAqui.backend.controllers;

import br.com.corteAqui.backend.dtos.request.ClienteRequestAtualizacaoDTO;
import br.com.corteAqui.backend.dtos.request.ClienteRequestDTO;
import br.com.corteAqui.backend.dtos.request.UsuarioRequestAtualizacaoDTO;
import br.com.corteAqui.backend.models.Usuario;
import br.com.corteAqui.backend.services.ClienteService;
import br.com.corteAqui.backend.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Gerenciador de clientes do CorteAqui")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @Operation(summary = "Cadastrar clientes", description = "Cadastra-se um novo cliente que não esteja salvo no sistema.")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> cadastrarCliente(@RequestBody @Valid ClienteRequestDTO corpoCliente) {
        clienteService.cadastrarCliente(corpoCliente);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso.");
    }

    @PutMapping("/editar/informacoes/cliente")
    @Operation(summary = "Atualizar cliente", description = "Atualiza-se as informações do cliente da sessão atual autenticada.")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<String> atualizarCliente(@AuthenticationPrincipal Usuario usuario, @RequestBody @Valid ClienteRequestAtualizacaoDTO corpoCliente) {
        clienteService.atualizarCliente(usuario.getId(), corpoCliente);
        return ResponseEntity.ok("Cliente atualizado com sucesso.");
    }

    @PutMapping("/editar/informacoes/usuario")
    @Operation(summary = "Atualizar usuário", description = "Atualiza-se as informações do usuário da sessão atual autenticada.")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<String> editarInformacoesUsuario(@AuthenticationPrincipal Usuario usuario, @RequestBody @Valid UsuarioRequestAtualizacaoDTO corpoUsuario) {
        usuarioService.atualizarUsuario(usuario.getId(), corpoUsuario.nomeUsuario());
        return ResponseEntity.ok("Usuário atualizado com sucesso.");
    }
}
