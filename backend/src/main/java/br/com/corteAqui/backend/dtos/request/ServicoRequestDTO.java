package br.com.corteAqui.backend.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ServicoRequestDTO(
        @NotNull
        @NotBlank(message = "O nome do serviço não pode ser vazio")
        @Size(min = 5, max = 255, message = "O nome do serviço deve ter pelo menos 5 caracteres")
        String nomeServico,

        @NotNull
        @Min(value = 1, message = "O preço do serviço deve ser maior ou igual a 0.")
        double preco,
        @NotNull(message = "ID da catecoria invalida.")
        Long idCategoria
) {
}
