package br.com.corteAqui.backend.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AgendamentoRequestDTO(
        @NotNull(message = "O Serviço não pode ser null!")
        @Min(1)
        Long idServico

) {
}
