package br.com.laudson.usuario_api.service;

import br.com.laudson.usuario_api.dto.CriarUsuarioRequestDTO;
import br.com.laudson.usuario_api.dto.CriarUsuarioResponseDTO;
import br.com.laudson.usuario_api.model.Usuario;
import br.com.laudson.usuario_api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public CriarUsuarioResponseDTO criarUsuario(CriarUsuarioRequestDTO request){
        Usuario usuario = new Usuario(
                request.nome(),
                request.email(),
                request.senha()
        );

        usuarioRepository.save(usuario);

        return new CriarUsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }
}
