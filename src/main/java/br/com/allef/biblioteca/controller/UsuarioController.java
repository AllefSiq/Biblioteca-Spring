package br.com.allef.biblioteca.controller;

import br.com.allef.biblioteca.models.Usuario;
import br.com.allef.biblioteca.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;


    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping(path = "cadastrarUsuario", consumes = "application/json")
    public ResponseEntity cadastrarUsuario(@RequestBody Usuario usuario){
        usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.OK).body("Usuario cadastrado com sucesso");
    }

    @GetMapping("/listarUsuarios")
    public List<Usuario> listadeUsuarios() {
        return usuarioService.findAll();
    }


    @DeleteMapping(path = "deletarUsuario/{id}")
    public ResponseEntity deletarUsuario(@PathVariable Long id){

        if (usuarioService.findByID(id).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
        }else {
            usuarioService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Usuario deletado com sucesso");
        }
    }

}
