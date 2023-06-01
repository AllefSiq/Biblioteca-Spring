package br.com.allef.biblioteca.service;

import br.com.allef.biblioteca.models.Aluguel;
import br.com.allef.biblioteca.models.Usuario;
import br.com.allef.biblioteca.repositories.AluguelRepository;
import br.com.allef.biblioteca.repositories.UsuarioRepository;
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

    public Optional<Usuario> findByID(Long id){
        return usuarioRepository.findById(id);
    }

    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }


    public List<Usuario> findAll() {
        return usuarioRepository.findAllByAtivoIsTrue();
    }


    public ServiceResponse delete(long usuarioId){
        Optional<Usuario> usuarioOptional = usuarioRepository.findByIdAndAtivoIsTrue(usuarioId);
        if (usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();
            Optional<List<Aluguel>> listaDeAluguel = aluguelRepository.findByUsuarioIdAndAtivoIsTrueAndDevolvidoIsFalse(usuarioId);
            if (listaDeAluguel.isPresent()) {
                List<Aluguel> alugueis = listaDeAluguel.get();
                if (!alugueis.isEmpty()) {
                    return new ServiceResponse(false, "Usuario com aluguel pendente");
                }
            }else {
                return new ServiceResponse(false,"Erro Interno");
            }
            usuario.setAtivo(false);
            usuarioRepository.save(usuario);
            return new ServiceResponse(true, "Usuario deletado com sucesso");

        }
        return new ServiceResponse(false, "Usuario nao encontrado");
    }

}
