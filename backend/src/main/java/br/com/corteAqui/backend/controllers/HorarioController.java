package br.com.corteAqui.backend.controllers;

import br.com.corteAqui.backend.models.Barbeiro;
import br.com.corteAqui.backend.models.Horario;
import br.com.corteAqui.backend.repositories.BarbeiroRepository;
import br.com.corteAqui.backend.services.HorarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/horarios")
public class HorarioController {

    private final HorarioService horarioService;
    private final BarbeiroRepository barbeiroRepository;

    public HorarioController(
            HorarioService horarioService,
            BarbeiroRepository barbeiroRepository) {
        this.horarioService = horarioService;
        this.barbeiroRepository = barbeiroRepository;
    }

    @PostMapping
    public ResponseEntity<Horario> cadastrar(
            @RequestBody Map<String, Object> dados) {

        Object barbeiroObjeto = dados.get("barbeiro");

        if (!(barbeiroObjeto instanceof Map<?, ?> barbeiroDados)
                || barbeiroDados.get("id") == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Informe o ID do barbeiro."
            );
        }

        Long barbeiroId;

        try {
            barbeiroId = Long.valueOf(
                    barbeiroDados.get("id").toString()
            );
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O ID do barbeiro é inválido."
            );
        }

        Barbeiro barbeiro = barbeiroRepository.findById(barbeiroId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Barbeiro não encontrado."
                ));

        Object dataObjeto = dados.get("dataHorario");
        Object horaObjeto = dados.get("hora");

        if (dataObjeto == null || horaObjeto == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Informe a data e a hora do horário."
            );
        }

        Horario horario = new Horario();

        try {
            horario.setDataHorario(
                    LocalDate.parse(dataObjeto.toString())
            );
            horario.setHora(
                    Time.valueOf(horaObjeto.toString())
            );
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A data ou a hora está em formato inválido."
            );
        }

        horario.setBarbeiro(barbeiro);

        Horario novoHorario = horarioService.cadastrar(horario);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novoHorario);
    }

    @GetMapping("/barbeiro/{barbeiroId}")
    public ResponseEntity<List<Horario>> listarPorBarbeiro(
            @PathVariable Long barbeiroId) {

        return ResponseEntity.ok(
                horarioService.listarPorBarbeiro(barbeiroId)
        );
    }

    @PutMapping("/{horarioId}/barbeiro/{barbeiroId}")
    public ResponseEntity<Horario> editar(
            @PathVariable Long horarioId,
            @PathVariable Long barbeiroId,
            @RequestBody Map<String, String> dados) {

        LocalDate novaData = null;
        Time novaHora = null;

        if (dados.containsKey("dataHorario")
                && dados.get("dataHorario") != null) {
            novaData = LocalDate.parse(dados.get("dataHorario"));
        }

        if (dados.containsKey("hora")
                && dados.get("hora") != null) {
            novaHora = Time.valueOf(dados.get("hora"));
        }

        Horario horarioAtualizado = horarioService.editar(
                horarioId,
                barbeiroId,
                novaData,
                novaHora
        );

        return ResponseEntity.ok(horarioAtualizado);
    }

    @DeleteMapping("/{horarioId}/barbeiro/{barbeiroId}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long horarioId,
            @PathVariable Long barbeiroId) {

        horarioService.excluir(horarioId, barbeiroId);

        return ResponseEntity.noContent().build();
    }
}