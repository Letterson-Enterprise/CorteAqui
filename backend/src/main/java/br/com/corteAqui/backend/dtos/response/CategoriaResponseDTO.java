package br.com.corteAqui.backend.dtos.response;

import br.com.corteAqui.backend.models.Servicos;

import java.util.List;

public record CategoriaResponseDTO(
        Long id,
        String nome,
        String descricao,
        List<ServicoResponseDTO> servicos
) {
}
