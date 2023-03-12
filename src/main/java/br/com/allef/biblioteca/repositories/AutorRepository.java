package br.com.allef.biblioteca.repositories;

import br.com.allef.biblioteca.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor,Integer> {


}
