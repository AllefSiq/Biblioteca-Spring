package br.com.allef.biblioteca.controller;


import br.com.allef.biblioteca.models.Autor;
import br.com.allef.biblioteca.service.AutorService;
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
    public List<Autor> listadeAutores() {
        return (List<Autor>) autorService.findAll();
    }


    @PostMapping(path = "cadastrarAutor", consumes = "application/json")
    public ResponseEntity cadastrarAutor(@RequestBody Autor autor){
        //System.out.println(autor);
        autorService.save(autor);
        return ResponseEntity.status(HttpStatus.OK).body("Autor cadastrado com sucesso");

    }


    @PutMapping(path = "atualizarAutor", consumes = "application/json")
    public ResponseEntity atualizarAutor(@RequestBody Autor autor){
        autorService.save(autor);
        return ResponseEntity.status(HttpStatus.OK).body("Autor atualizado com sucesso");
    }


    @DeleteMapping(path = "deletarAutor/{id}")
    public ResponseEntity deletarAutor(@PathVariable Integer id){

        if (autorService.findById(id).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor não encontrado");
        }else {
            Optional<Autor> autorOptional = autorService.findById(id);
            Autor autor = autorOptional.get();
            autorService.delete(autor);
            return ResponseEntity.status(HttpStatus.OK).body("Autor deletado com sucesso");
        }
    }
}
