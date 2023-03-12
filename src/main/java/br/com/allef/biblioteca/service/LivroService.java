package br.com.allef.biblioteca.service;

import br.com.allef.biblioteca.models.Livro;
import br.com.allef.biblioteca.repositories.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {
    final LivroRepository livroRepository;


    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public Livro save(Livro livro){
        return livroRepository.save(livro);
    }

    public List saveAll(List<Livro> lista){
       return livroRepository.saveAll(lista);
    }
}
