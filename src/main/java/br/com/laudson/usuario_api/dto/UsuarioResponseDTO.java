package br.com.laudson.usuario_api.dto;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email
) {
}
