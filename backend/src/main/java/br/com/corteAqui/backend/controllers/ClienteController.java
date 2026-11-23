package br.com.corteAqui.backend.controllers;

import br.com.corteAqui.backend.dtos.request.ClienteRequestAtualizacaoDTO;
import br.com.corteAqui.backend.dtos.request.ClienteRequestDTO;
import br.com.corteAqui.backend.dtos.request.UsuarioRequestAtualizacaoDTO;
import br.com.corteAqui.backend.models.Cliente;
import br.com.corteAqui.backend.models.Usuario;
import br.com.corteAqui.backend.services.ClienteService;
import br.com.corteAqui.backend.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import br.com.corteAqui.backend.dtos.response.HorarioResponseDTO;
import br.com.corteAqui.backend.services.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private HorarioService horarioService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<String> cadastrarCliente(@RequestBody @Valid ClienteRequestDTO corpoCliente) {
        clienteService.cadastrarCliente(corpoCliente);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso.");
    }

    @PutMapping("/editar/informacoes/cliente")
    public ResponseEntity<String> atualizarCliente(@AuthenticationPrincipal Usuario usuario, @RequestBody @Valid ClienteRequestAtualizacaoDTO corpoCliente) {
        clienteService.atualizarCliente(usuario.getId(), corpoCliente);
        return ResponseEntity.ok("Cliente atualizado com sucesso.");
    }

    @PutMapping("/editar/informacoes/usuario")
    public ResponseEntity<String> editarInformacoesUsuario(@AuthenticationPrincipal Usuario usuario, @RequestBody @Valid UsuarioRequestAtualizacaoDTO corpoUsuario) {
        usuarioService.atualizarUsuario(usuario.getId(), corpoUsuario.nomeUsuario());
        return ResponseEntity.ok("Usuário atualizado com sucesso.");
    }
    @GetMapping("/barbeiros/{barbeiroId}/horarios-disponiveis")
    public ResponseEntity<List<HorarioResponseDTO>> listarHorariosDisponiveis(
        @PathVariable Long barbeiroId) {

    return ResponseEntity.ok(horarioService.listarDisponiveis(barbeiroId));
    }
}
