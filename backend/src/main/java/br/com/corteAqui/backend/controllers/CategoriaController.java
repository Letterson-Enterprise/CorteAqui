package br.com.corteAqui.backend.controllers;

import br.com.corteAqui.backend.dtos.request.CategoriaRequestDTO;
import br.com.corteAqui.backend.dtos.response.CategoriaResponseDTO;
import br.com.corteAqui.backend.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.listarTodoasCastegoiras());
    }

    @PostMapping
    public ResponseEntity<String> cadastrarCategoria(@RequestBody CategoriaRequestDTO corpoCategoriaRequisicao) {
        categoriaService.cadastrarCategoria(corpoCategoriaRequisicao);
        return ResponseEntity.status(HttpStatus.CREATED).body("Categoria cadastrada com sucesso.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> visualizarCategoria(@PathVariable Long id) {
        CategoriaResponseDTO categoriaBuscada = categoriaService.visualizarCategoria(id);
        return ResponseEntity.status(HttpStatus.OK).body(categoriaBuscada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizarCategoria(@RequestBody CategoriaRequestDTO novosDadosCategoria, @PathVariable Long id) {
        CategoriaResponseDTO categoriaAtualizada = categoriaService.atualizarCategoria(novosDadosCategoria, id);
        return ResponseEntity.status(HttpStatus.OK).body(categoriaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarCategoria(@PathVariable Long id) {
        categoriaService.deletarCategoria(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Categoria deletada com sucesso.");
    }
}
