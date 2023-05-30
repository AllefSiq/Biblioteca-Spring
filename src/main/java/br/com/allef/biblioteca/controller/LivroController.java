package br.com.allef.biblioteca.controller;

import br.com.allef.biblioteca.models.Autor;
import br.com.allef.biblioteca.models.Livro;
import br.com.allef.biblioteca.service.AutorService;
import br.com.allef.biblioteca.service.LivroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;
    private final AutorService autorService;


    public LivroController(LivroService livroService, AutorService autorService) {
        this.livroService = livroService;
        this.autorService = autorService;
    }

    @GetMapping("/listarLivros")
    public List<Livro> listadeLivros() {
        return (List<Livro>) livroService.findAll();
    }


    @GetMapping("/livrosPorAutor/{id}")
    public ResponseEntity getLivrosPorAutor(@PathVariable Integer id){

        Optional<Autor> autor = autorService.findById(id);
        if(autor.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(autor.get().getLivros());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor não encontrado");
        }
    }

    @PostMapping(path = "cadastrarLivro", consumes = "application/json")
    public ResponseEntity cadastrarLivro(@RequestBody Map<String,Object> requestBody) throws ParseException {

        List<Integer> autores = (List<Integer>) requestBody.get("autores");
        Livro livro = new Livro();
        livro.setNome((String) requestBody.get("nome"));
        String dataString = (String) requestBody.get("lancamento");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date data = format.parse(dataString);
        livro.setLancamento(data);
        livro.setCategoria((String) requestBody.get("categoria"));
        livro.setNumEstoque((Integer) requestBody.get("numEstoque"));
        for (Integer autor : autores){
            Optional<Autor> autorDoBanco = autorService.findById(autor);
            if (autorDoBanco.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor não encontrado");

            livro.getAutores().add(autorDoBanco.get());
            livroService.save(livro);
            autorDoBanco.get().getLivros().add(livro);
            autorService.save(autorDoBanco.get());

        }
        return ResponseEntity.status(HttpStatus.OK).body("Livro cadastrado com sucesso");

    }




}
