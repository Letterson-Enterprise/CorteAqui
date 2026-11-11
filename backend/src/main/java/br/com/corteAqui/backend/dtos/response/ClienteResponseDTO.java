package br.com.corteAqui.backend.dtos.response;

public record ClienteResponseDTO(
        String nome,
        String sobreNome,
        String nomeUsuario,
        String email,
        String senha
) {
}
