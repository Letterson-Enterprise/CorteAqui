package br.com.corteAqui.backend.dtos.request;

import jakarta.validation.constraints.*;

public record BarbeiroRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        @NotNull(message = "Nome nulo")
        @Size(min = 8, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
        String nome,

        @NotBlank(message = "O sobrenome é obrigatório")
        @NotNull(message = "Sobrenome nulo")
        String sobreNome,

        @NotBlank(message = "O CPF é obrigatório")
        @NotNull(message = "CPF nulo")
        @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 caracteres numéricos")
        String cpf,

        @NotBlank(message = "O nome de usuário é obrigatório")
        @NotNull(message = "nome de usuário nulo")
        @Size(min = 5)
        String nomeUsuario,

        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Formato de e-mail inválido")
        @NotNull(message = "Email nulo")
        String email,

        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*\\W).*$",
                message = "A senha deve conter pelo menos uma letra maiúscula, um número e um caractere especial."
        )
        @Size(min = 8, message = "A senha deve ter, no mínimo, 8 caracteres")
        @NotNull(message = "Senha nula")
        String senha
) {
}
