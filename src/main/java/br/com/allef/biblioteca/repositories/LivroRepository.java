package br.com.allef.biblioteca.repositories;

import br.com.allef.biblioteca.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro,Long> {
    @Override
    default <livro extends Livro> livro saveAndFlush(livro entity) {
        return null;
    }



    Livro findByNome(String nome);

    @Override
    Optional<Livro> findById(Long id);




}
