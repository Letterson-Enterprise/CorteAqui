package br.com.corteAqui.backend.controllers;

import br.com.corteAqui.backend.dtos.request.AgendamentoRequestDTO;
import br.com.corteAqui.backend.models.Usuario;
import br.com.corteAqui.backend.services.AgendamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Agendamentos", description = "Controllador resposavel por receber requisições para a gendamentos")
@SecurityRequirement(name = "bearer")
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @PostMapping("/{idHorario}")
    @Operation(summary = "Criar Agendamento", description = "Criar o agendamento com base no horário escolhido junto com serviço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agendamento criado e marcado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Requisição não autorizada."),
            @ApiResponse(responseCode = "500", description = "Erro Interno do servidor!"),
            @ApiResponse(responseCode = "400", description = "Requisições invalidas!")
    })
    public ResponseEntity<String> fazerAgendamento(@PathVariable Long idHorario, @AuthenticationPrincipal Usuario usuario, @Valid @RequestBody AgendamentoRequestDTO corpoRequisicao) {
        agendamentoService.realizarAgendament(corpoRequisicao, usuario.getId(), idHorario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Agendamento marcado com sucesso!");
    }
}
