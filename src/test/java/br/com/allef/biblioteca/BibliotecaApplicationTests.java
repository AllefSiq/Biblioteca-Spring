package br.com.allef.biblioteca;


import br.com.allef.biblioteca.models.Autor;
import br.com.allef.biblioteca.models.Livro;
import br.com.allef.biblioteca.service.AutorService;
import br.com.allef.biblioteca.service.LivroService;
import br.com.allef.biblioteca.service.UsuarioService;
import org.junit.Before;
import org.junit.BeforeClass;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional


class BibliotecaApplicationTests {

	private final LivroService livroService;
	private final AutorService autorService;
	private final MockMvc mockMvc;
	private final UsuarioService usuarioService;




	@Autowired
	public BibliotecaApplicationTests(LivroService livroService, AutorService autorService, MockMvc mockMvc, UsuarioService usuarioService) {

		this.livroService = livroService;
		this.autorService = autorService;
		this.mockMvc = mockMvc;
		this.usuarioService = usuarioService;
	}




	//teste de conexão com o banco de dados.

	@Test
	void testaConexaoComBanco(){
		assertNotNull(livroService);
		assertNotNull(autorService);
		assertNotNull(usuarioService);
	}



	@Test
	void contextLoads() {
	}

	@Test
	public void testCadastrarLivro() throws Exception {

		String requestBody = "{\"nome\":\"Turma da Monica\",\"lancamento\":\"2023-03-14T02:56:58.929+00:00\",\"numEstoque\":45,\"autores\":[1],\"categoria\":\"Humor\"}";
		mockMvc.perform(MockMvcRequestBuilders.post("/autores/cadastrarAutor").contentType("application/json").content(requestBody))
				.andExpect(status().isOk());


	}

	//Teste de cadastro de autor
	@Test
	public void testCadastrarAutor() throws Exception {
		String requestBody = "{\"nome\": \"Teste\", \"dataDeNascimentp\": \"2022-05-16\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("/autores/cadastrarAutor").contentType("application/json").content(requestBody))
				.andExpect(status().isOk());


	}

	//Teste de cadastro de usuario
	@Test
	public void testCadastrarUsuario() throws Exception {
		String requestBody = "{\"nome\": \"Teste\", \"dataDeNascimento\": \"2022-05-16\" , \"email\":\"allef@hotmail.com\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("/autores/cadastrarAutor").contentType("application/json").content(requestBody))
				.andExpect(status().isOk());
	}

	//Teste de listagem de livros
	@Test
	public void testListarLivros() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/livros/listarLivros"))
				.andExpect(status().isOk());

	}
	//Testa a listagem de autores
	@Test
	public void testListarAutores() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/autores/listarAutores"))
				.andExpect(status().isOk());

	}



	//Teste de listagem de usuarios
	@Test
	public void testListarUsuarios() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/listarUsuarios"))
				.andExpect(status().isOk());

	}


}
