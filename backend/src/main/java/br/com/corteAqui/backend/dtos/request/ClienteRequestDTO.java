package br.com.corteAqui.backend.dtos.request;

public record ClienteRequestDTO(
        String nome,
        String sobreNome,
        String nomeUsuario,
        String email,
        String senha
) {
}
