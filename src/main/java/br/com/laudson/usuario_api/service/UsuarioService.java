package br.com.laudson.usuario_api.service;

import br.com.laudson.usuario_api.dto.CriarUsuarioRequestDTO;
import br.com.laudson.usuario_api.dto.CriarUsuarioResponseDTO;
import br.com.laudson.usuario_api.model.Usuario;
import br.com.laudson.usuario_api.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CriarUsuarioResponseDTO criarUsuario(CriarUsuarioRequestDTO request){

        String senhaHash = passwordEncoder.encode(request.senha());

        Usuario usuario = new Usuario(
                request.nome(),
                request.email(),
                senhaHash
        );

        usuarioRepository.save(usuario);

        return new CriarUsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }
}
