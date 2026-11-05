package br.com.corteAqui.backend.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record ClienteRequestAtualizacaoDTO(
        @NotBlank String nome,
        @NotBlank String sobreNome
) {}
