package br.com.corteAqui.backend.dtos.response;

import br.com.corteAqui.backend.models.Barbeiro;
import br.com.corteAqui.backend.models.Categoria;

public record ServicoResponseDTO(
        Long id,
        String nomeServico,
        double preco
) {
}
