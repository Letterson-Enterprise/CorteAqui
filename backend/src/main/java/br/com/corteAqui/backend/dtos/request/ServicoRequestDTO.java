package br.com.corteAqui.backend.dtos.request;

import br.com.corteAqui.backend.models.Barbeiro;
import br.com.corteAqui.backend.models.Categoria;

public record ServicoRequestDTO(
        String nomeServico,
        double preco,
        Long idCategoria
) {
}
