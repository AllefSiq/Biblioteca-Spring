package br.com.allef.biblioteca.repositories;

import br.com.allef.biblioteca.models.Autor;
import br.com.allef.biblioteca.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor,Integer> {


    Autor findByLivros(Livro livro);


    @Override
    default void deleteById(Integer integer) {
        
    }


    @Override
    default void delete(Autor entity) {
    }
}
