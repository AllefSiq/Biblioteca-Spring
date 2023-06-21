package br.com.allef.biblioteca;


import br.com.allef.biblioteca.models.Autor;
import br.com.allef.biblioteca.models.Livro;
import br.com.allef.biblioteca.models.Usuario;
import br.com.allef.biblioteca.service.AutorService;
import br.com.allef.biblioteca.service.LivroService;
import br.com.allef.biblioteca.service.UsuarioService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class BibliotecaApplicationTests {

    private final LivroService livroService;
    private final AutorService autorService;
    private final MockMvc mockMvc;
    private final UsuarioService usuarioService;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Autowired
    public BibliotecaApplicationTests(LivroService livroService, AutorService autorService, MockMvc mockMvc, UsuarioService usuarioService) {


        this.livroService = livroService;
        this.autorService = autorService;
        this.mockMvc = mockMvc;
        this.usuarioService = usuarioService;
    }


    //teste de conexão com o banco de dados.

    @Test
    void testaConexaoComBanco() {
        assertNotNull(livroService);
        assertNotNull(autorService);
        assertNotNull(usuarioService);
    }


    @Test
    void contextLoads() {
    }

    @Test
    @DirtiesContext
    public void testCadastrarLivro() throws Exception {

        String requestBodyAutor = "{\"nome\": \"Teste\", \"dataDeNascimentp\": \"2022-05-16\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/autores/cadastrarAutor").contentType("application/json").content(requestBodyAutor))
                .andExpect(status().isOk());


        String requestBodyLivro = "{\"nome\":\"Turma da Monica\",\"lancamento\":\"2023-03-14T02:56:58.929+00:00\",\"numEstoque\":45,\"autores\":[1],\"categoria\":\"Humor\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/livros/cadastrarLivro").contentType("application/json").content(requestBodyLivro));


    }

    //Teste de cadastro de autor
    @Test
    @DirtiesContext
    public void testCadastrarAutor() throws Exception {
        String requestBody = "{\"nome\": \"Teste\", \"dataDeNascimentp\": \"2022-05-16\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/autores/cadastrarAutor").contentType("application/json").content(requestBody))
                .andExpect(status().isOk());


    }

    //Teste de cadastro de usuario
    @Test
    @DirtiesContext
    public void testCadastrarUsuario() throws Exception {
        String requestBody = "{\"nome\": \"Teste\", \"dataDeNascimento\": \"2022-05-16\" , \"email\":\"allef@hotmail.com\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios/cadastrarUsuario").contentType("application/json").content(requestBody))
                .andExpect(status().isOk());
    }

    //Teste de listagem de livros
    @Test
    @DirtiesContext
    public void testListarLivros() throws Exception {

        //cadastrar autor
        String requestBodyAutor = "{\"nome\": \"Teste\", \"dataDeNascimento\": \"2022-05-16\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/autores/cadastrarAutor").contentType("application/json").content(requestBodyAutor));

        //cadastrar livro
        String requestBodyLivro = "{\"nome\":\"Turma da Monica\",\"lancamento\":\"2023-03-14T02:56:58.929+00:00\",\"numEstoque\":45,\"autores\":[1],\"categoria\":\"Humor\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/livros/cadastrarLivro").contentType("application/json").content(requestBodyLivro));

        //cadastrar livro 2
        String requestBodyLivro2 = "{\"nome\":\"Turma do chaves\",\"lancamento\":\"2023-03-14T02:56:58.929+00:00\",\"numEstoque\":45,\"autores\":[1],\"categoria\":\"Humor\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/livros/cadastrarLivro").contentType("application/json").content(requestBodyLivro2));

        //listar livros
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/livros/listarLivros"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<Livro> livros = Arrays.asList(objectMapper.readValue(result, Livro[].class));
        assertEquals(2, livros.size());
    }

    @Test
    @DirtiesContext
    public void testListarLivrosPorAutor() throws Exception {
        //Cadastra um autor
        String requestBodyAutor = "{\"nome\": \"Teste\", \"dataDeNascimento\": \"2022-05-16\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/autores/cadastrarAutor").contentType("application/json").content(requestBodyAutor));

        //Cadastra 2 livros
        String requestBodyLivro = "{\"nome\":\"Turma da Monica\",\"lancamento\":\"2023-03-14T02:56:58.929+00:00\",\"numEstoque\":45,\"autores\":[1],\"categoria\":\"Humor\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/livros/cadastrarLivro").contentType("application/json").content(requestBodyLivro));

        String requestBodyLivro2 = "{\"nome\":\"Turma do chaves\",\"lancamento\":\"2023-03-14T02:56:58.929+00:00\",\"numEstoque\":45,\"autores\":[1],\"categoria\":\"Humor\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/livros/cadastrarLivro").contentType("application/json").content(requestBodyLivro2));


        String response = mockMvc.perform(MockMvcRequestBuilders.get("/livros/livrosPorAutor/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();


        List<Livro> livros = Arrays.asList(objectMapper.readValue(response, Livro[].class));
        assertEquals(2, livros.size());
    }

    //Testa a listagem de autores
    @Test
    @DirtiesContext
    public void testListarAutores() throws Exception {

        //Cadastra um autor
        String requestBodyAutor = "{\"nome\": \"Teste\", \"dataDeNascimento\": \"2022-05-16\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/autores/cadastrarAutor").contentType("application/json").content(requestBodyAutor));

        //Cadastra outro autor
        String requestBodyAutor2 = "{\"nome\": \"Teste2\", \"dataDeNascimento\": \"2022-05-16\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/autores/cadastrarAutor").contentType("application/json").content(requestBodyAutor2));


        String response = mockMvc.perform(MockMvcRequestBuilders.get("/autores/listarAutores"))
                .andExpect(status().isOk()).andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        List<Autor> autores = Arrays.asList(objectMapper.readValue(response, Autor[].class));
        assertEquals(2, autores.size());

    }


    //Teste de listagem de usuarios
    @Test
    @DirtiesContext
    public void testListarUsuarios() throws Exception {
        //cadastrar um usuario
        String requestBody = "{\"nome\": \"Teste\", \"dataDeNascimento\": \"2022-05-16\" , \"email\":\"teste@hotmail.com\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios/cadastrarUsuario").contentType("application/json").content(requestBody));

        //cadastrar outro usuario
        String requestBody2 = "{\"nome\": \"Teste2\", \"dataDeNascimento\": \"2022-05-16\" , \"email\":\"teste2@hotmail.com\"}";


        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios/cadastrarUsuario").contentType("application/json").content(requestBody2));

        String response = mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/listarUsuarios"))
                .andExpect(status().isOk()).andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        List<Usuario> usuarios = Arrays.asList(objectMapper.readValue(response, Usuario[].class));
        assertEquals(2, usuarios.size());

    }

    @Test
    @DirtiesContext
    public void testeListarAlugueisPorUsuario() throws Exception {
        //Cadastra um usuario
        String requestBodyUsuario = "{\"nome\": \"Teste\", \"dataDeNascimento\": \"2022-05-16\" , \"email\":\"teste@hotmail.com\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios/cadastrarUsuario").contentType("application/json").content(requestBodyUsuario));

        //cadastrarum autor
        String requestBodyAutor = "{\"nome\": \"Teste\", \"dataDeNascimento\": \"2022-05-16\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/autores/cadastrarAutor").contentType("application/json").content(requestBodyAutor));

        //Cadastra um livro
        String requestBodyLivro = "{\"nome\":\"Turma da Monica\",\"lancamento\":\"2023-03-14T02:56:58.929+00:00\",\"numEstoque\":45,\"autores\":[1],\"categoria\":\"Humor\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/livros/cadastrarLivro").contentType("application/json").content(requestBodyLivro));

        //Cadastra um aluguel
        String requestBodyAluguel = "{\"livroId\":1,\"usuarioId\":1}";
        mockMvc.perform(MockMvcRequestBuilders.post("/aluguel/novoAluguel").contentType("application/json").content(requestBodyAluguel));

        String response = mockMvc.perform(MockMvcRequestBuilders.get("/aluguel/listarAlugueis/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Map<String, String>> map = objectMapper.readValue(response, new TypeReference<List<Map<String, String>>>() {
        });

        System.out.println(map.get(0));
        assertEquals(1, map.size());

    }

    @Test
    @DirtiesContext
    public void testAlugarLivro() throws Exception {
        //Cadastra um usuario
        String requestBodyUsuario = "{\"nome\": \"Teste\", \"dataDeNascimento\": \"2022-05-16\" , \"email\":\"teste@hotmail.com\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios/cadastrarUsuario").contentType("application/json").content(requestBodyUsuario));

        //cadastrarum autor
        String requestBodyAutor = "{\"nome\": \"Teste\", \"dataDeNascimento\": \"2022-05-16\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/autores/cadastrarAutor").contentType("application/json").content(requestBodyAutor));

        //Cadastra um livro
        String requestBodyLivro = "{\"nome\":\"Turma da Monica\",\"lancamento\":\"2023-03-14T02:56:58.929+00:00\",\"numEstoque\":45,\"autores\":[1],\"categoria\":\"Humor\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/livros/cadastrarLivro").contentType("application/json").content(requestBodyLivro));

        //Cadastra um aluguel
        String requestBodyAluguel = "{\"livroId\":1,\"usuarioId\":1}";
        mockMvc.perform(MockMvcRequestBuilders.post("/aluguel/novoAluguel").contentType("application/json").content(requestBodyAluguel)).andExpect(status().isOk());
    }

    @Test
    @DirtiesContext
    public void testDevolverLivro() throws Exception {
        //Cadastra um usuario
        String requestBodyUsuario = "{\"nome\": \"Teste\", \"dataDeNascimento\": \"2022-05-16\" , \"email\":\"teste@hotmail.com\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios/cadastrarUsuario").contentType("application/json").content(requestBodyUsuario));

        //cadastrarum autor
        String requestBodyAutor = "{\"nome\": \"Teste\", \"dataDeNascimento\": \"2022-05-16\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/autores/cadastrarAutor").contentType("application/json").content(requestBodyAutor));

        //Cadastra um livro
        String requestBodyLivro = "{\"nome\":\"Turma da Monica\",\"lancamento\":\"2023-03-14T02:56:58.929+00:00\",\"numEstoque\":45,\"autores\":[1],\"categoria\":\"Humor\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/livros/cadastrarLivro").contentType("application/json").content(requestBodyLivro));

        //Cadastra um aluguel
        String requestBodyAluguel = "{\"livroId\":1,\"usuarioId\":1}";
        mockMvc.perform(MockMvcRequestBuilders.post("/aluguel/novoAluguel").contentType("application/json").content(requestBodyAluguel));

        String requestBodyDevolver = "{\"livroId\":1,\"usuarioId\":1}";
        mockMvc.perform(MockMvcRequestBuilders.post("/aluguel/devolverLivro").contentType("application/json").content(requestBodyDevolver))
                .andExpect(status().isOk());
    }


    @Test
    @DirtiesContext
    public void testaDeletarLivro() throws Exception {


        String requestBodyAutor = "{\"nome\": \"Teste\", \"dataDeNascimento\": \"2022-05-16\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/autores/cadastrarAutor").contentType("application/json").content(requestBodyAutor));


        String requestBodyLivro = "{\"nome\":\"Turma da Monica\",\"lancamento\":\"2023-03-14T02:56:58.929+00:00\",\"numEstoque\":45,\"autores\":[1],\"categoria\":\"Humor\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/livros/cadastrarLivro").contentType("application/json").content(requestBodyLivro));


        mockMvc.perform(MockMvcRequestBuilders.delete("/livros/deletarLivro/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DirtiesContext
    public void testeDeletarAutor() throws Exception {
        String requestBodyAutor = "{\"nome\": \"Teste\", \"dataDeNascimento\": \"2022-05-16\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/autores/cadastrarAutor").contentType("application/json").content(requestBodyAutor))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.delete("/autores/deletarAutor/1"))
                .andExpect(status().isOk());


    }

    @Test
    @DirtiesContext
    public void testeDeletarUsuario() throws Exception {
        String requestBody = "{\"nome\": \"Teste\", \"dataDeNascimento\": \"2022-05-16\" , \"email\":\"allef@hotmail.com\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios/cadastrarUsuario").contentType("application/json").content(requestBody))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.delete("/usuarios/deletarUsuario/1")).andExpect(status().isOk());
    }

    @Test
    @DirtiesContext
    public void testeDeletarUsuarioComAluguelPendente() throws Exception {
        String requestBody = "{\"nome\": \"Teste\", \"dataDeNascimento\": \"2022-05-16\" , \"email\":\"allef@hotmail.com\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios/cadastrarUsuario").contentType("application/json").content(requestBody))
                .andExpect(status().isOk());

        //criar um autor
        String requestBodyAutor = "{\"nome\": \"Teste\", \"dataDeNascimento\": \"2022-05-16\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/autores/cadastrarAutor").contentType("application/json").content(requestBodyAutor))
                .andExpect(status().isOk());

        //criar um livro
        String requestBodyLivro = "{\"nome\":\"Turma da Monica\",\"lancamento\":\"2023-03-14T02:56:58.929+00:00\",\"numEstoque\":45,\"autores\":[1],\"categoria\":\"Humor\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/livros/cadastrarLivro").contentType("application/json").content(requestBodyLivro));

        //alugar o livro
        String requestBodyAluguel = "{\"livroId\":1,\"usuarioId\":1}";
        mockMvc.perform(MockMvcRequestBuilders.post("/aluguel/novoAluguel").contentType("application/json").content(requestBodyAluguel));

        mockMvc.perform(MockMvcRequestBuilders.delete("/usuarios/deletarUsuario/1")).andExpect(status().isBadRequest());
    }
}
