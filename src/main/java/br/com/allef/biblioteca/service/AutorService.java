package br.com.allef.biblioteca.service;

import br.com.allef.biblioteca.models.Autor;
import br.com.allef.biblioteca.models.Livro;
import br.com.allef.biblioteca.repositories.AutorRepository;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

    final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Autor save(Autor autor){
        return autorRepository.save(autor);
    }


}
