package br.com.allef.biblioteca.service;


import br.com.allef.biblioteca.exceptions.ErroDeInsercaoException;
import br.com.allef.biblioteca.models.Autor;
import br.com.allef.biblioteca.models.Livro;
import br.com.allef.biblioteca.repositories.AutorRepository;
import br.com.allef.biblioteca.repositories.LivroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LivroService {
    final LivroRepository livroRepository;
    final AutorRepository autorRepository;

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public Livro save(Livro livro) {
        return livroRepository.save(livro);
    }

    public List<Livro> saveAll(List<Livro> lista) {
        return livroRepository.saveAll(lista);
    }


    public Optional<Livro> findById(Long id) {
        return livroRepository.findByIdAndAtivoIsTrue(id);
    }


    public Livro findByNome(String nome) {
        return livroRepository.findByNome(nome);
    }


    public Iterable<Livro> findAll() {
        return livroRepository.findAllByAtivoIsTrue();
    }


    public ServiceResponse delete(long livroId) {
        Optional<Livro> livroOptional = livroRepository.findById(livroId);
        if (livroOptional.isPresent()) {
            Livro livro = livroOptional.get();
            for (int i = 0; i < livro.getAutores().size(); i++) {
                Optional<Autor> autoreslivro = autorRepository.findById(livro.getAutores().get(i).getId());
                if (autoreslivro.isPresent()) {
                    Autor autor = autoreslivro.get();
                    autor.getLivros().remove(livro);
                    autorRepository.save(autor);
                }
            }
            livro.setAtivo(false);
            livroRepository.save(livro);
            return new ServiceResponse(true, "Livro deletado com sucesso", HttpStatus.OK);
        } else {
            return new ServiceResponse(false, "Livro nao encontrado", HttpStatus.NOT_FOUND);
        }
    }


    public ServiceResponse buscarLivrosPorAutor(long autorId) {
        Optional<Autor> autorOptional = autorRepository.findByIdAndAtivoIsTrue(autorId);
        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            List<Livro> livros = autor.getLivros();
            return new ServiceResponse(true, livros.toString(), HttpStatus.OK);
        } else {
            return new ServiceResponse(false, "Autor nao encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional(rollbackFor = ErroDeInsercaoException.class)
    public ServiceResponse cadastrarLivro(Map<String, Object> requestBody) {
        try {
            List<Integer> autores = (List<Integer>) requestBody.get("autores");
            Livro livro = new Livro();
            livro.setNome((String) requestBody.get("nome"));
            String dataString = (String) requestBody.get("lancamento");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date data = format.parse(dataString);
            livro.setLancamento(data);
            livro.setCategoria((String) requestBody.get("categoria"));
            livro.setNumEstoque((Integer) requestBody.get("numEstoque"));

            List<Optional<Autor>> listaDeAutores = new ArrayList<>();
            for (long autor : autores) {
                Optional<Autor> autorDoBanco = autorRepository.findByIdAndAtivoIsTrue(autor);
                if (autorDoBanco.isEmpty()) {
                    throw new ErroDeInsercaoException("Autor não encontrado");
                } else {
                    listaDeAutores.add(autorDoBanco);
                }
            }
            if (listaDeAutores.size() == autores.size()) {
                for (Optional<Autor> autorDoBanco : listaDeAutores) {
                    livro.getAutores().add(autorDoBanco.get());
                    livroRepository.save(livro);
                    autorDoBanco.get().getLivros().add(livro);
                    autorRepository.save(autorDoBanco.get());
                }
            } else {
                throw new ErroDeInsercaoException("Erro ao cadastrar livro");
            }
            return new ServiceResponse(true, "Livro cadastrado com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            return new ServiceResponse(false, e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
