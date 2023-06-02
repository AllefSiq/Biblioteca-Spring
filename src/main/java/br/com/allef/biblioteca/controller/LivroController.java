package br.com.allef.biblioteca.controller;

import br.com.allef.biblioteca.models.Autor;
import br.com.allef.biblioteca.models.Livro;
import br.com.allef.biblioteca.service.AutorService;
import br.com.allef.biblioteca.service.LivroService;
import br.com.allef.biblioteca.service.ServiceResponse;
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
    public ResponseEntity getLivrosPorAutor(@PathVariable Long id){

        Optional<Autor> autor = autorService.findById(id);
        if(autor.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(autor.get().getLivros());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor não encontrado");
        }
    }

    @PostMapping(path = "cadastrarLivro", consumes = "application/json")
    public ResponseEntity cadastrarLivro(@RequestBody Map<String,Object> requestBody) throws ParseException {
        ServiceResponse response = livroService.cadastrarLivro(requestBody);
        if (response.isSuccess())
            return ResponseEntity.status(HttpStatus.OK).body(response.getMessage());
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.getMessage());
    }

    @DeleteMapping(path = "deletarLivro/{livroId}")
    public ResponseEntity deletarLivro(@PathVariable Long livroId){
        ServiceResponse response = livroService.delete(livroId);
        if (response.isSuccess())
            return ResponseEntity.status(HttpStatus.OK).body(response.getMessage());
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.getMessage());
    }




}
