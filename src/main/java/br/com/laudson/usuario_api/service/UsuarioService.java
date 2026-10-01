package br.com.laudson.usuario_api.service;

import br.com.laudson.usuario_api.dto.AtualizarUsuarioRequestDTO;
import br.com.laudson.usuario_api.dto.CriarUsuarioRequestDTO;
import br.com.laudson.usuario_api.dto.CriarUsuarioResponseDTO;
import br.com.laudson.usuario_api.dto.UsuarioResponseDTO;
import br.com.laudson.usuario_api.exception.EmailJaCadastradoException;
import br.com.laudson.usuario_api.exception.UsuarioNaoEncontradoException;
import br.com.laudson.usuario_api.model.Usuario;
import br.com.laudson.usuario_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public CriarUsuarioResponseDTO criarUsuario(CriarUsuarioRequestDTO request){

        if (usuarioRepository.findByEmail(request.email()).isPresent()){
            throw new EmailJaCadastradoException("Email já cadastrado");
        }

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

    public List<UsuarioResponseDTO> listarUsuarios(){
        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> new UsuarioResponseDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail()
                ))
                .toList();
    }

    public UsuarioResponseDTO buscarPorId(Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail());
    }

    public UsuarioResponseDTO atualizarUsuario(Long id, AtualizarUsuarioRequestDTO request){

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        usuarioRepository.findByEmail(request.email())
                .ifPresent(usuarioEncontrado -> {
                    if (!usuarioEncontrado.getId().equals(id)){
                        throw new EmailJaCadastradoException("Este e-mail já está cadastrado");
                    }
                });

        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setSenha(passwordEncoder.encode(request.senha()));

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(
                usuarioAtualizado.getId(),
                usuarioAtualizado.getNome(),
                usuarioAtualizado.getEmail()
        );
    }

    public void deletarUsuario(Long id){

        if (!usuarioRepository.existsById(id)){
            throw new UsuarioNaoEncontradoException("Usuário não encontrado");
        }

        usuarioRepository.deleteById(id);
    }
}
