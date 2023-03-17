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


    @GetMapping("/autores")
    public List<Autor> listadeAutores() {
        return (List<Autor>) autorService.findAll();
    }

    @GetMapping("/livros")
    public List<Livro> listadeLivros() {
        return (List<Livro>) livroService.findAll();
    }

    //metodo que retorna a lista de livros de um autor
    @GetMapping("/livros/{id}")
    public ResponseEntity getLivros(@PathVariable Integer id){
        Optional<Autor> autor = autorService.findById(id);
        if(autor.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(autor.get().getLivros());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor não encontrado");
        }
    }

    @GetMapping("/usuarios")
    public List<Usuario> listadeUsuarios() {
        return usuarioService.findAll();
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
            if (!autorDoBanco.isPresent())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor não encontrado");

            livro.getAutores().add(autorDoBanco.get());
            livroService.save(livro);
            autorDoBanco.get().getLivros().add(livro);
            autorService.save(autorDoBanco.get());

        }
        return ResponseEntity.status(HttpStatus.OK).body("Livro cadastrado com sucesso");

        }
    //metoddo para cadastrar um autor

    @PostMapping(path = "cadastrarAutor", consumes = "application/json")
    public ResponseEntity cadastrarAutor(@RequestBody Autor autor){
        //System.out.println(autor);
        autorService.save(autor);
        return ResponseEntity.status(HttpStatus.OK).body("Autor cadastrado com sucesso");

    }
    //metodo para cadastrar um Usuario
    @PostMapping(path = "cadastrarUsuario", consumes = "application/json")
    public ResponseEntity cadastrarUsuario(@RequestBody Usuario usuario){
            usuarioService.save(usuario);
            return ResponseEntity.status(HttpStatus.OK).body("Usuario cadastrado com sucesso");
    }

    @PostMapping(path = "novoAluguel", consumes = "application/json")
    public ResponseEntity novoAluguel(@RequestBody Map<String,Long> requestBody) throws ParseException{
        Long livroId = requestBody.get("livroId");
        Long usuarioId = requestBody.get("usuarioId");

        if (!aluguelService.alugarLivro(livroId, usuarioId))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario com devolução pendente");
        return ResponseEntity.status(HttpStatus.OK).body("Aluguel realizado com sucesso");


    }

}



