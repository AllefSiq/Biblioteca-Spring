package br.com.allef.biblioteca.controller;

import br.com.allef.biblioteca.models.Autor;
import br.com.allef.biblioteca.models.Livro;
import br.com.allef.biblioteca.models.Usuario;
import br.com.allef.biblioteca.service.AluguelService;
import br.com.allef.biblioteca.service.AutorService;
import br.com.allef.biblioteca.service.LivroService;
import br.com.allef.biblioteca.service.UsuarioService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;



@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/biblioteca")
public class bibliotecaController {

    final LivroService livroService;
    final AutorService autorService;
    final UsuarioService usuarioService;
    final AluguelService aluguelService;

    public bibliotecaController(LivroService livroService,
                                AutorService autorService,
                                UsuarioService usuarioService,
                                AluguelService aluguelService) {


        this.livroService = livroService;
        this.autorService = autorService;
        this.usuarioService = usuarioService;
        this.aluguelService = aluguelService;
    }





    //metodo que retorna a lista de livros de um autor






    //metoddo para cadastrar um autor


    //metodo para cadastrar um Usuario



}



