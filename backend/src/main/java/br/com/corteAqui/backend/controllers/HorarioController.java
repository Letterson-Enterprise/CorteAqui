package br.com.corteAqui.backend.controllers;

import br.com.corteAqui.backend.dtos.request.HorarioEdicaoRequestDTO;
import br.com.corteAqui.backend.dtos.request.HorarioRequestDTO;
import br.com.corteAqui.backend.dtos.response.HorarioResponseDTO;
import br.com.corteAqui.backend.models.Usuario;
import br.com.corteAqui.backend.services.HorarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/horarios")
@Tag(name = "Horários", description = "Gerenciador de horários do CorteAqui")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @PostMapping
    @Operation(summary = "Cadastrar horários", description = "Cadastra-se um novo horário que não esteja salvo no sistema.")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> cadastrar(@RequestBody  @Valid HorarioRequestDTO dados, @AuthenticationPrincipal Usuario usuarioLogado) {
        horarioService.cadastrar(dados, usuarioLogado.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("Horário cadastrado com sucesso");
    }

    @GetMapping
    @Operation(summary = "Listar horários por barbeiro", description = "Lista-se os horários registradas no sistema, atrelados ao barbeiro.")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<List<HorarioResponseDTO>> listarPorBarbeiro(@AuthenticationPrincipal Usuario usuarioLogado) {
        return ResponseEntity.ok(horarioService.listarPorBarbeiro(usuarioLogado.getId()));
    }

    @PutMapping("/{horarioId}")
    @Operation(summary = "Atualizar horário", description = "A partir do ID, atualiza-se um horário escolhido.")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<String> editar(@PathVariable Long horarioId, @RequestBody @Valid HorarioEdicaoRequestDTO dados, @AuthenticationPrincipal Usuario usuarioLogado) {
        horarioService.editar(horarioId, usuarioLogado.getId(), dados);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Horário editado com sucesso!");
    }

    @DeleteMapping("/{horarioId}")
    @Operation(summary = "Deletar horário", description = "A partir do ID, apaga-se um horário escolhido.")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<String> excluir(@PathVariable Long horarioId, @AuthenticationPrincipal Usuario usuarioLogado) {
        horarioService.excluir(horarioId, usuarioLogado.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Horário deletado com sucesso!");
    }
}
