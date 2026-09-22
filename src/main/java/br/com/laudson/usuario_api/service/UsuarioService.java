package br.com.laudson.usuario_api.service;

import br.com.laudson.usuario_api.dto.CriarUsuarioRequestDTO;
import br.com.laudson.usuario_api.model.Usuario;
import br.com.laudson.usuario_api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario criarUsuario(CriarUsuarioRequestDTO request){
        Usuario usuario = new Usuario(
                request.nome(),
                request.email(),
                request.senha()
        );

        return usuarioRepository.save(usuario);
    }
}
