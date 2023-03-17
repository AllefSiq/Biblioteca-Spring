package br.com.allef.biblioteca.repositories;

import br.com.allef.biblioteca.models.Aluguel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AluguelRepository extends JpaRepository<Aluguel,Long> {

    @Override
    Optional<Aluguel> findById(Long aLong);

    //Optional<Aluguel> findByUsuarioId(Long id);

    //retorna uma lista de alugueis de um usuario
    Optional<List<Aluguel>> findByUsuarioId(Long id);
}
