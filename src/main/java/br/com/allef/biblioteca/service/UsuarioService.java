package br.com.allef.biblioteca.service;

import br.com.allef.biblioteca.models.Usuario;
import br.com.allef.biblioteca.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;

    }

    public Optional<Usuario> findByID(Long id){
        return usuarioRepository.findById(id);
    }

    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }


    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }


}
