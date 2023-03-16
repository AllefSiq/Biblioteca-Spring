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

    public List<Livro> saveAll(List<Livro> lista){
       return livroRepository.saveAll(lista);
    }

    //metodo para buscar livro por id
    public Livro findById(Long id){
        return livroRepository.findById(id).get();
    }

    //metodo para buscar livro por nome
    public Livro findByNome(String nome){
        return livroRepository.findByNome(nome);
    }

    //metodo para retornar uma lista de livros
    public Iterable<Livro> findAll(){
        return livroRepository.findAll();
    }


    public void cadastrarLivro(Livro livro) {


    }
}
