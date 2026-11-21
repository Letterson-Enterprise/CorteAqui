package br.com.corteAqui.backend.controllers;

import br.com.corteAqui.backend.dtos.request.CategoriaRequestDTO;
import br.com.corteAqui.backend.dtos.response.CategoriaResponseDTO;
import br.com.corteAqui.backend.services.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Categoria", description = "Gerenciador de categorias do CorteAqui")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    @Operation(summary = "Listar todas as categorias", description = "Lista-se todas as categorias registradas no sistema.")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.listarTodoasCastegoiras());
    }

    @PostMapping
    @Operation(summary = "Cadastrar categorias", description = "Cadastra-se uma nova categoria que não esteja salvo no sistema.")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> cadastrarCategoria(@RequestBody CategoriaRequestDTO corpoCategoriaRequisicao) {
        categoriaService.cadastrarCategoria(corpoCategoriaRequisicao);
        return ResponseEntity.status(HttpStatus.CREATED).body("Categoria cadastrada com sucesso.");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Listar categorias específicas", description = "A partir do ID, exibirá as informações da categoria escolhida.")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<CategoriaResponseDTO> visualizarCategoria(@PathVariable Long id) {
        CategoriaResponseDTO categoriaBuscada = categoriaService.visualizarCategoria(id);
        return ResponseEntity.status(HttpStatus.OK).body(categoriaBuscada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar categoria", description = "A partir do ID, atualiza-se uma categoria escolhida.")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<CategoriaResponseDTO> atualizarCategoria(@RequestBody CategoriaRequestDTO novosDadosCategoria, @PathVariable Long id) {
        CategoriaResponseDTO categoriaAtualizada = categoriaService.atualizarCategoria(novosDadosCategoria, id);
        return ResponseEntity.status(HttpStatus.OK).body(categoriaAtualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar categoria", description = "A partir do ID, apaga-se uma categoria escolhida.")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<String> deletarCategoria(@PathVariable Long id) {
        categoriaService.deletarCategoria(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Categoria deletada com sucesso.");
    }
}
