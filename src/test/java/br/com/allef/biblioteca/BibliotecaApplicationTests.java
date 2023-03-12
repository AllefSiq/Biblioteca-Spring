package br.com.allef.biblioteca;

import br.com.allef.biblioteca.service.AutorService;
import br.com.allef.biblioteca.service.LivroService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BibliotecaApplicationTests {

	private LivroService livroService;
	private AutorService autorService;

	public BibliotecaApplicationTests(LivroService livroService, AutorService autorService) {
		this.livroService = livroService;
		this.autorService = autorService;
	}

	@Test
	void contextLoads() {
	}

	@Test
	void TestaCriacaoDeAutorELivro(){



	}

}
