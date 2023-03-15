package br.com.allef.biblioteca.service;

import br.com.allef.biblioteca.models.Autor;
import br.com.allef.biblioteca.models.Livro;
import br.com.allef.biblioteca.repositories.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutorService {

    final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Autor save(Autor autor){
        return autorRepository.save(autor);
    }

    public Optional<Autor> findById(Integer id){
        return autorRepository.findById(id);
    }

    //metodo para retornar uma lista de livros
    public Iterable<Autor> findAll(){
        return autorRepository.findAll();
    }

    public Autor findByLivros(Livro livro){
        return autorRepository.findByLivros(livro);
    }



}
