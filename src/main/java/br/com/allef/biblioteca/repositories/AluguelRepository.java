package br.com.allef.biblioteca.repositories;

import br.com.allef.biblioteca.models.Aluguel;
import br.com.allef.biblioteca.models.Livro;
import br.com.allef.biblioteca.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AluguelRepository extends JpaRepository<Aluguel,Long> {

    Optional<Aluguel> findByIdAndAtivoIsTrue(Long aLong);




    //retorna uma lista de alugueis de um usuario
    Optional<List<Aluguel>> findByUsuarioIdAndAtivoIsTrue(Long usuarioId);


    


}
