package br.com.allef.biblioteca.service;

import br.com.allef.biblioteca.models.Aluguel;
import br.com.allef.biblioteca.models.Usuario;
import br.com.allef.biblioteca.repositories.AluguelRepository;
import br.com.allef.biblioteca.repositories.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final AluguelRepository aluguelRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, AluguelRepository aluguelRepository) {
        this.usuarioRepository = usuarioRepository;
        this.aluguelRepository = aluguelRepository;
    }

    public Optional<Usuario> findByID(Long id) {
        return usuarioRepository.findById(id);
    }

    public ServiceResponse save(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            return new ServiceResponse(false, "Email ja cadastrado", HttpStatus.BAD_REQUEST);
        }
        usuarioRepository.save(usuario);
        return new ServiceResponse(true, "Usuario cadastrado com sucesso", HttpStatus.OK);
    }


    public List<Usuario> findAll() {
        return usuarioRepository.findAllByAtivoIsTrue();
    }


    public ServiceResponse delete(long usuarioId) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByIdAndAtivoIsTrue(usuarioId);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            Optional<List<Aluguel>> listaDeAluguel = aluguelRepository.findByUsuarioIdAndAtivoIsTrueAndDevolvidoIsFalse(usuarioId);
            if (listaDeAluguel.isPresent()) {
                List<Aluguel> alugueis = listaDeAluguel.get();
                if (!alugueis.isEmpty()) {
                    return new ServiceResponse(false, "Usuario com aluguel pendente", HttpStatus.FORBIDDEN);
                }
            } else {
                return new ServiceResponse(false, "Erro Interno", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            usuario.setAtivo(false);
            usuarioRepository.save(usuario);
            return new ServiceResponse(true, "Usuario deletado com sucesso", HttpStatus.OK);

        }
        return new ServiceResponse(false, "Usuario nao encontrado", HttpStatus.NOT_FOUND);
    }

}
