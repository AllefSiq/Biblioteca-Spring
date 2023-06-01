package br.com.allef.biblioteca.service;


import br.com.allef.biblioteca.models.Autor;
import br.com.allef.biblioteca.models.Livro;
import br.com.allef.biblioteca.repositories.AutorRepository;
import br.com.allef.biblioteca.repositories.LivroRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class LivroService {
    final LivroRepository livroRepository;
    final AutorRepository autorRepository;

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public Livro save(Livro livro){
        return livroRepository.save(livro);
    }

    public List<Livro> saveAll(List<Livro> lista){
       return livroRepository.saveAll(lista);
    }

    //metodo para buscar livro por id
    public Optional<Livro> findById(Long id){
        return livroRepository.findByIdAndAtivoIsTrue(id);
    }

    //metodo para buscar livro por nome
    public Livro findByNome(String nome){
        return livroRepository.findByNome(nome);
    }

    //metodo para retornar uma lista de livros
    public Iterable<Livro> findAll(){
        return livroRepository.findAllByAtivoIsTrue();
    }


    public ServiceResponse delete(long livroId){
        Optional<Livro> livroOptional = livroRepository.findById(livroId);
        if (livroOptional.isPresent()) {
            Livro livro = livroOptional.get();
            for (int i = 0; i < livro.getAutores().size(); i++) {
                Optional<Autor> autoreslivro = autorRepository.findById(livro.getAutores().get(i).getId());
                if(autoreslivro.isPresent()){
                    Autor autor = autoreslivro.get();
                    autor.getLivros().remove(livro);
                    autorRepository.save(autor);
                }
            }
            livro.setAtivo(false);
            livroRepository.save(livro);
            return new ServiceResponse(true,"Livro deletado com sucesso");
        }else{
            return new ServiceResponse(false,"Livro nao encontrado");
        }
    }

    public void cadastrarLivro(Livro livro) {


    }
}
