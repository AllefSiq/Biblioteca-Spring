package br.com.allef.biblioteca.controller;


import br.com.allef.biblioteca.models.Autor;
import br.com.allef.biblioteca.service.AutorService;
import br.com.allef.biblioteca.service.ServiceResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }


    @GetMapping("/listarAutores")
    @Cacheable("lista-de-autores")
    public List<Autor> listadeAutores() {
        return (List<Autor>) autorService.findAll();
    }


    @PostMapping(path = "cadastrarAutor", consumes = "application/json")
    public ResponseEntity cadastrarAutor(@RequestBody Autor autor) {
        //System.out.println(autor);
        autorService.save(autor);
        return ResponseEntity.status(HttpStatus.OK).body("Autor cadastrado com sucesso");

    }


    @PutMapping(path = "atualizarAutor", consumes = "application/json")
    public ResponseEntity atualizarAutor(@RequestBody Autor autor) {
        autorService.save(autor);
        return ResponseEntity.status(HttpStatus.OK).body("Autor atualizado com sucesso");
    }


    @DeleteMapping(path = "deletarAutor/{autorId}")
    public ResponseEntity deletarAutor(@PathVariable Long autorId) {
        ServiceResponse response = autorService.delete(autorId);
        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.OK).body(response.getMessage());
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.getMessage());

    }
}
