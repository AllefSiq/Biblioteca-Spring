package br.com.allef.biblioteca.service;

import br.com.allef.biblioteca.models.Aluguel;
import br.com.allef.biblioteca.models.Livro;
import br.com.allef.biblioteca.models.Usuario;
import br.com.allef.biblioteca.repositories.AluguelRepository;
import br.com.allef.biblioteca.repositories.LivroRepository;
import br.com.allef.biblioteca.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AluguelService {

    private final AluguelRepository aluguelRepository;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;


    public AluguelService(AluguelRepository aluguelRepository, LivroRepository livroRepository, UsuarioRepository usuarioRepository) {
        this.aluguelRepository = aluguelRepository;
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    //metodo para verificar se um usuario ja alugou um livro

    //metodo para alugar um livro
    public boolean alugarLivro(Long livroId, Long usuarioId) {
        Optional<Livro> livro = livroRepository.findById(livroId);
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
        if (livro.isPresent() && usuario.isPresent() && verificarAluguel(usuarioId)) {
            Aluguel aluguelNovo = new Aluguel();
            Livro livro1 = livro.get();
            if (livro1.retirarDoEstoque()) {
                aluguelNovo.setLivro(livro1);
                aluguelNovo.setUsuario(usuario.get());
                aluguelNovo.setDataAluguel(new Date());
                aluguelNovo.setDataDevolucao(new Date(System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000)));
                livroRepository.save(livro1);
                aluguelRepository.save(aluguelNovo);
                return true;
            }
            return false;
        }
        return false;
    }


    //big O notation O(n)
    public boolean verificarAluguel(Long usuarioId) {
        List<Aluguel> aluguelList = aluguelRepository.findByUsuarioId(usuarioId).orElse(Collections.emptyList());
        return aluguelList.stream().filter(Aluguel::isDevolvido).count() == aluguelList.size();
    }
}


