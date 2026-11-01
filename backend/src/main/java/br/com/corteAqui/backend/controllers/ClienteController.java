package br.com.corteAqui.backend.controllers;

import br.com.corteAqui.backend.dtos.request.ClienteRequestDTO;
import br.com.corteAqui.backend.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<String> cadastrarCliente(@RequestBody ClienteRequestDTO corpoCliente) {
        clienteService.cadastrarCliente(corpoCliente);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario cadastrado com sucesso.");
    }
}
