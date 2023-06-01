package br.com.allef.biblioteca.repositories;

import br.com.allef.biblioteca.models.Autor;
import br.com.allef.biblioteca.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor,Long> {



    @Override
    default void deleteById(Long autorId) {
        
    }



    @Override
    default void delete(Autor entity) {
    }

    Iterable<Autor> findAllByAtivoIsTrue();


    Autor findByLivrosAndAtivoIsTrue(Livro livro);


    Optional<Autor> findByIdAndAtivoIsTrue(Long autorId);


}
