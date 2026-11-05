package br.com.corteAqui.backend.dtos.request;

public record BarbeiroRequestDTO(
        String nome,
        String sobreNome,
        String cpf,
        String nomeUsuario,
        String email,
        String senha
) {
}
