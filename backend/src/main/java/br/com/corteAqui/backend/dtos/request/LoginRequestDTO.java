package br.com.corteAqui.backend.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(
        @NotNull
        @NotBlank(message = "O nome de usuario deve ser preenchido.")
        String nomeUsuario,

        @NotNull
        @NotBlank(message = "A senha deve ser preenchida.")
        String senha
) {
}
