package br.com.laudson.usuario_api.dto;

public record CriarUsuarioResponseDTO(
        Long id,
        String nome,
        String email
) {
}
