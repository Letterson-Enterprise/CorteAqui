package br.com.corteAqui.backend.dtos.request;

public record LoginRequestDTO(
   String nomeUsuario,
   String senha
) {}
