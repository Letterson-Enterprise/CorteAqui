package br.com.corteAqui.backend.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteRequestDTO(
        @NotBlank String nome,
        @NotBlank String sobreNome,
        @NotBlank String nomeUsuario,
        @NotBlank @Email String email,
        String senha
) {
}
