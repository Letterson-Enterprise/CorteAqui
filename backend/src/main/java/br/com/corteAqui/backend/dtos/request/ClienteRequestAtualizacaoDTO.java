package br.com.corteAqui.backend.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record ClienteRequestAtualizacaoDTO(
        @NotBlank(message = "O nome é obrigatório.") String nome,
        @NotBlank(message = "O sobrenome é obrigatório.") String sobreNome
) {}
