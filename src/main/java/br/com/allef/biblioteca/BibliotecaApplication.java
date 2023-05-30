package br.com.allef.biblioteca;

import br.com.allef.biblioteca.models.Autor;
import br.com.allef.biblioteca.models.Livro;
import br.com.allef.biblioteca.service.LivroService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
@RestController
@EnableTransactionManagement
@SpringBootApplication
public class BibliotecaApplication {





	public static void main(String[] args) {
		SpringApplication.run(BibliotecaApplication.class, args);


	}

}
