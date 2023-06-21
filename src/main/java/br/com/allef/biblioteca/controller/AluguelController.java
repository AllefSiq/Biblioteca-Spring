package br.com.allef.biblioteca.controller;

import br.com.allef.biblioteca.models.Aluguel;
import br.com.allef.biblioteca.service.AluguelService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/aluguel")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AluguelController {

    private final AluguelService aluguelService;

    public AluguelController(AluguelService aluguelService) {
        this.aluguelService = aluguelService;
    }


    @PostMapping(path = "novoAluguel", consumes = "application/json")
    public ResponseEntity novoAluguel(@RequestBody Map<String, Long> requestBody) {
        Long livroId = requestBody.get("livroId");
        Long usuarioId = requestBody.get("usuarioId");

        if (!aluguelService.alugarLivro(livroId, usuarioId))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario com devolução pendente");
        return ResponseEntity.status(HttpStatus.OK).body("Aluguel realizado com sucesso");


    }

    @PutMapping(path = "devolverLivro", consumes = "application/json")
    public ResponseEntity devoloverLivro(@RequestBody Map<String, Long> requestBody) {
        Long livroId = requestBody.get("livroId");
        Long usuarioId = requestBody.get("usuarioId");
        if (!aluguelService.devolverLivro(livroId, usuarioId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não possui aluguel pendente");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Livro devolvido com sucesso");


    }

    @GetMapping(path = "listarAlugueis/{usuarioId}")
    public List listarAlugueis(@PathVariable Long usuarioId) {
        List<Aluguel> aluguelList = aluguelService.listarAlugueisPorUsuario(usuarioId);
        if (!aluguelList.isEmpty()) {
            return aluguelList;
        }
        return null;

    }


}
