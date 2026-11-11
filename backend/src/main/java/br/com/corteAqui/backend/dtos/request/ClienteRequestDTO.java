package br.com.corteAqui.backend.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteRequestDTO(
        @NotBlank(message = "O nome é obrigatório.") String nome,
        @NotBlank(message = "O sobrenome é obrigatório.") String sobreNome,
        @NotBlank(message = "É preciso definir o nome de usuário.") String nomeUsuario,

        @NotBlank(message = "E-mail é obrigatório.")
        @Email(message = "Formato de e-mail inválido.")
        String email,

        @NotBlank(message = "É preciso definir a senha")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*\\W).*$",
                message = "A senha deve conter pelo menos uma letra maiúscula, um número e um caractere especial."
        )
        @Size(min = 8, message = "A senha deve ter, no mínimo, 8 caracteres")
        String senha
) {
}
