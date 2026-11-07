package br.com.corteAqui.backend.controllers;

import br.com.corteAqui.backend.dtos.request.ServicoRequestDTO;
import br.com.corteAqui.backend.dtos.response.CategoriaResponseDTO;
import br.com.corteAqui.backend.dtos.response.ServicoResponseDTO;
import br.com.corteAqui.backend.models.Usuario;
import br.com.corteAqui.backend.services.ServicosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {
    @Autowired
    private ServicosService servicosService;

    @GetMapping
    public ResponseEntity<List<ServicoResponseDTO>> listarTodos(@AuthenticationPrincipal Usuario usuarioLogado) {
        return ResponseEntity.status(HttpStatus.OK).body(servicosService.listarTodosServicos(usuarioLogado.getId()));
    }

    @PostMapping
    public ResponseEntity<String> cadastrarServico(@RequestBody ServicoRequestDTO corpoServicoRequisicao, @AuthenticationPrincipal Usuario usuarioAtivo) {
        System.out.println(usuarioAtivo.getPapel());
        System.out.println(usuarioAtivo.getId());
        System.out.println(usuarioAtivo.getAuthorities());
        System.out.println(usuarioAtivo.getNomeUsuario());
        servicosService.cadastrarServico(corpoServicoRequisicao, usuarioAtivo.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("Serviço cadastrada com sucesso.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoResponseDTO> visualizarServico(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioAtivo) {
        ServicoResponseDTO servicoBuscado = servicosService.visualizarServico(id, usuarioAtivo.getId());
        return ResponseEntity.status(HttpStatus.OK).body(servicoBuscado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoResponseDTO> atualizarServico(@RequestBody ServicoRequestDTO novosDadosServico, @PathVariable Long id, @AuthenticationPrincipal Usuario usuarioAtivo) {
        System.out.println(usuarioAtivo.getPapel());
        System.out.println(usuarioAtivo.getId());
        System.out.println(usuarioAtivo.getAuthorities());
        System.out.println(usuarioAtivo.getNomeUsuario());
        ServicoResponseDTO servicoAtualizado = servicosService.atualizarServico(novosDadosServico, id,usuarioAtivo.getId());
        return ResponseEntity.status(HttpStatus.OK).body(servicoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarServico(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioAtivo) {
        servicosService.deletarServico(id,usuarioAtivo.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Serviço deletado com sucesso.");
    }
}
