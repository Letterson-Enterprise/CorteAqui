package br.com.corteAqui.backend.services;

import br.com.corteAqui.backend.dtos.request.CategoriaRequestDTO;
import br.com.corteAqui.backend.dtos.response.CategoriaResponseDTO;
import br.com.corteAqui.backend.dtos.response.ServicoResponseDTO;
import br.com.corteAqui.backend.exceptions.ExcecaoCamposInvalidos;
import br.com.corteAqui.backend.exceptions.ExcecaoListaVazia;
import br.com.corteAqui.backend.exceptions.ExecaoInformacoesDuplicadas;
import br.com.corteAqui.backend.exceptions.RecursoNaoEncontradoExcecao;
import br.com.corteAqui.backend.models.Categoria;
import br.com.corteAqui.backend.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public void cadastrarCategoria(CategoriaRequestDTO categoria) {
        if(categoria.nome().isBlank() || categoria.descricao().isBlank()) {
            throw new ExcecaoCamposInvalidos("Todos os campos devem ser preenchidos", "ERROR");
        }

        boolean categoriaExistente = categoriaRepository.existsByNome(categoria.nome());

        if(categoriaExistente) {
            throw new ExecaoInformacoesDuplicadas("Já existe uma categoria com esse nome", "Informações duplicacas");
        }

        Categoria novaCategoria = new Categoria();

        novaCategoria.setNome(categoria.nome());
        novaCategoria.setDescricao(categoria.descricao());
        categoriaRepository.save(novaCategoria);
    }


    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listarTodoasCastegoiras() {
        List<CategoriaResponseDTO> todasCategorias = categoriaRepository.findAll().stream().map(this::converterEmCategoriaResponseDTO).toList();

        if(todasCategorias.isEmpty()) {
            throw new ExcecaoListaVazia("Não há categorias cadastradas", "Lista vazia");
        }

        return todasCategorias;
    }


    public CategoriaResponseDTO converterEmCategoriaResponseDTO(Categoria categoria) {
        List<ServicoResponseDTO> todoServicos = categoria.getServicos().stream().map(servicos -> new ServicoResponseDTO(servicos.getId(),servicos.getNomeServico(), servicos.getPreco())).toList();
        return new CategoriaResponseDTO(categoria.getId(),categoria.getNome(), categoria.getDescricao(), todoServicos);
    }

    public Categoria buscarCategoriaPorId(Long id) {
        Categoria categoriaBuscasda = categoriaRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoExcecao("Categoria não encontrada", "Recurso não encontrado"));

        return categoriaBuscasda;
    }

    public CategoriaResponseDTO atualizarCategoria(CategoriaRequestDTO novosDadosCategoria, Long id) {
        Categoria categoriaBuscada = buscarCategoriaPorId(id);

        if(novosDadosCategoria.nome().isBlank() || novosDadosCategoria.descricao().isBlank()) {
            throw new ExcecaoCamposInvalidos("Todos os campos devem ser preenchidos", "ERROR");
        }

        List<Categoria> categoriasExistentes = categoriaRepository.verificarSeNomeExisteEmOutraLinha(novosDadosCategoria.nome(), categoriaBuscada.getId());

        if(!categoriasExistentes.isEmpty()) {
            throw new ExecaoInformacoesDuplicadas("Já existe uma categoria com esse nome", "Informações duplicacas");
        }

        categoriaBuscada.setNome(novosDadosCategoria.nome());
        categoriaBuscada.setDescricao(novosDadosCategoria.descricao());

        Categoria categoriaAtualizada = categoriaRepository.save(categoriaBuscada);

        return converterEmCategoriaResponseDTO(categoriaAtualizada);
    }

    public void deletarCategoria(Long id) {
        Categoria categoriaBuscada = buscarCategoriaPorId(id);
        categoriaRepository.delete(categoriaBuscada);
    }

    public CategoriaResponseDTO visualizarCategoria(Long id) {
        Categoria categoriaBuscada = buscarCategoriaPorId(id);
        return converterEmCategoriaResponseDTO(categoriaBuscada);
    }

}
