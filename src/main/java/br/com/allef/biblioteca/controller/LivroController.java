package br.com.allef.biblioteca.controller;

import br.com.allef.biblioteca.models.Autor;
import br.com.allef.biblioteca.models.Livro;
import br.com.allef.biblioteca.service.AutorService;
import br.com.allef.biblioteca.service.LivroService;
import br.com.allef.biblioteca.service.ServiceResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Cacheable("lista-livros")
    public List<Livro> listadeLivros() {
        return (List<Livro>) livroService.findAll();
    }


    @GetMapping("/livrosPorAutor/{id}")
    public ResponseEntity livrosPorAutor(@PathVariable Long id) {
        List livros = livroService.livrosPorAutor(id);
        if (livros.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor não encontrado");
        else
            return ResponseEntity.status(HttpStatus.OK).body(livros);
    }

    @PostMapping(path = "cadastrarLivro", consumes = "application/json")
    public ResponseEntity cadastrarLivro(@RequestBody Map<String, Object> requestBody) throws ParseException {
        ServiceResponse response = livroService.cadastrarLivro(requestBody);
        if (response.isSuccess())
            return ResponseEntity.status(HttpStatus.OK).body(response.getMessage());
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.getMessage());
    }

    @DeleteMapping(path = "deletarLivro/{livroId}")
    public ResponseEntity deletarLivro(@PathVariable Long livroId) {
        ServiceResponse response = livroService.delete(livroId);
        if (response.isSuccess())
            return ResponseEntity.status(HttpStatus.OK).body(response.getMessage());
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.getMessage());
    }


}
