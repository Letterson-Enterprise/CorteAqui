package br.com.corteAqui.backend.controllers;

import br.com.corteAqui.backend.dtos.request.HorarioEdicaoRequestDTO;
import br.com.corteAqui.backend.dtos.request.HorarioRequestDTO;
import br.com.corteAqui.backend.dtos.response.HorarioResponseDTO;
import br.com.corteAqui.backend.dtos.response.MensagemResponseDTO;
import br.com.corteAqui.backend.services.HorarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/horarios")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class HorarioController {

    private final HorarioService horarioService;

    @PostMapping
    public ResponseEntity<HorarioResponseDTO> cadastrar(
            @RequestBody HorarioRequestDTO dados) {

        HorarioResponseDTO horario = horarioService.cadastrar(dados);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(horario);
    }

    @GetMapping("/barbeiro/{barbeiroId}")
    public ResponseEntity<List<HorarioResponseDTO>> listarPorBarbeiro(
            @PathVariable Long barbeiroId) {

        return ResponseEntity.ok(
                horarioService.listarPorBarbeiro(barbeiroId)
        );
    }

    @PutMapping("/{horarioId}/barbeiro/{barbeiroId}")
    public ResponseEntity<MensagemResponseDTO> editar(
            @PathVariable Long horarioId,
            @PathVariable Long barbeiroId,
            @RequestBody HorarioEdicaoRequestDTO dados) {

        String mensagem = horarioService.editar(
                horarioId,
                barbeiroId,
                dados
        );

        return ResponseEntity.ok(
                new MensagemResponseDTO(mensagem)
        );
    }

    @DeleteMapping("/{horarioId}/barbeiro/{barbeiroId}")
    public ResponseEntity<MensagemResponseDTO> excluir(
            @PathVariable Long horarioId,
            @PathVariable Long barbeiroId) {

        String mensagem = horarioService.excluir(
                horarioId,
                barbeiroId
        );

        return ResponseEntity.ok(
                new MensagemResponseDTO(mensagem)
        );
    }
}
