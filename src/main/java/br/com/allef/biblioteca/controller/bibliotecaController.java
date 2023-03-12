package br.com.allef.biblioteca.controller;

import br.com.allef.biblioteca.models.Autor;
import br.com.allef.biblioteca.models.Livro;
import br.com.allef.biblioteca.service.AutorService;
import br.com.allef.biblioteca.service.LivroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/biblioteca")
public class bibliotecaController {

    final LivroService livroService;
    final AutorService autorService;

    public bibliotecaController(LivroService livroService, AutorService autorService) {
        this.livroService = livroService;
        this.autorService = autorService;
    }

    @GetMapping("/teste")
    public ResponseEntity save(){
        Autor Ziraldo = new Autor(2L,"Ziraldo",new Date());
        Livro OMMarrom = new Livro("O menino marrom", new Date(),Ziraldo, "Humor",45);

        autorService.save(Ziraldo);
        livroService.save(OMMarrom);


        return ResponseEntity.status(HttpStatus.OK).body("Sucesso");

    }
}
