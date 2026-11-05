package br.com.corteAqui.backend.controllers;

import br.com.corteAqui.backend.dtos.request.BarbeiroRequestDTO;
import br.com.corteAqui.backend.services.BarbeiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/barbeiro")
public class BarbeiroController {
    @Autowired
    private BarbeiroService barbeiroService;

    @PostMapping
    public ResponseEntity<String> cadastrarBarbeiro(@RequestBody BarbeiroRequestDTO corpoNovoBarbeiro) {
        barbeiroService.cadastrarBarbeiro(corpoNovoBarbeiro);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário Criado com sucesso");
    }
}
