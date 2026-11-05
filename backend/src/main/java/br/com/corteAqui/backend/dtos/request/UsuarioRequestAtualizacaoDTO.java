package br.com.corteAqui.backend.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record UsuarioRequestAtualizacaoDTO(
        @NotBlank String nomeUsuario
) {}
