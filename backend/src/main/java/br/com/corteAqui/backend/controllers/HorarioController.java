package br.com.corteAqui.backend.controllers;

import br.com.corteAqui.backend.dtos.request.HorarioEdicaoRequestDTO;
import br.com.corteAqui.backend.dtos.request.HorarioRequestDTO;
import br.com.corteAqui.backend.dtos.response.HorarioResponseDTO;
import br.com.corteAqui.backend.dtos.response.MensagemResponseDTO;
import br.com.corteAqui.backend.models.Usuario;
import br.com.corteAqui.backend.services.HorarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/horarios")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody  @Valid HorarioRequestDTO dados, @AuthenticationPrincipal Usuario usuarioLogado) {

        horarioService.cadastrar(dados, usuarioLogado.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body("Horario Cadastrado com sucesso");
    }

    @GetMapping
    public ResponseEntity<List<HorarioResponseDTO>> listarPorBarbeiro(@AuthenticationPrincipal Usuario usuarioLogado) {
        return ResponseEntity.ok(horarioService.listarPorBarbeiro(usuarioLogado.getId()));
    }

    @PutMapping("/{horarioId}")
    public ResponseEntity<String> editar(@PathVariable Long horarioId, @RequestBody @Valid HorarioEdicaoRequestDTO dados, @AuthenticationPrincipal Usuario usuarioLogado) {

         horarioService.editar(horarioId, usuarioLogado.getId(), dados);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Horario editado com sucesso!");
    }

    @DeleteMapping("/{horarioId}")
    public ResponseEntity<String> excluir(@PathVariable Long horarioId, @AuthenticationPrincipal Usuario usuarioLogado) {
        horarioService.excluir(horarioId, usuarioLogado.getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Horario deletado com sucesso!");
    }
}
