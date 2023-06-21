package br.com.allef.biblioteca.service;

import br.com.allef.biblioteca.models.Autor;
import br.com.allef.biblioteca.models.Livro;
import br.com.allef.biblioteca.repositories.AutorRepository;
import br.com.allef.biblioteca.repositories.LivroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    final LivroRepository livroRepository;
    final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository, LivroRepository livroRepository) {

        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
    }

    public Autor save(Autor autor) {
        return autorRepository.save(autor);
    }

    public Optional<Autor> findById(Long id) {
        return autorRepository.findById(id);
    }

    //metodo para retornar uma lista de livros
    public Iterable<Autor> findAll() {
        return autorRepository.findAllByAtivoIsTrue();
    }

    public Autor findByLivros(Livro livro) {
        return autorRepository.findByLivrosAndAtivoIsTrue(livro);
    }


    @Transactional
    public void deleteById(Long id) {
        autorRepository.deleteById(id);
    }


    public ServiceResponse delete(Long autorId) {
        Optional<Autor> autorOptional = autorRepository.findByIdAndAtivoIsTrue(autorId);
        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            autor.setAtivo(false);
            autor.getLivros().clear();
            autorRepository.save(autor);
            return new ServiceResponse(true, "Autor deletado com sucesso", HttpStatus.OK);


        } else return new ServiceResponse(false, "Autor nao Encontrado", HttpStatus.NOT_FOUND);

    }

}
