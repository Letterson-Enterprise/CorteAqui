package br.com.corteAqui.backend.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record UsuarioRequestAtualizacaoDTO(
        @NotBlank(message = "O nome de usuário é obrigatório") String nomeUsuario
) {}
