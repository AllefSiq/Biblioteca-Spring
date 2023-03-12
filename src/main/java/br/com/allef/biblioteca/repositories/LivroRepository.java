package br.com.allef.biblioteca.repositories;

import br.com.allef.biblioteca.models.Livro;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro,Integer> {
    @Override
    default <livro extends Livro> livro saveAndFlush(livro entity) {
        return null;
    }
}
