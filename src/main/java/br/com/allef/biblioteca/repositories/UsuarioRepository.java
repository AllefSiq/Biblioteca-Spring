package br.com.allef.biblioteca.repositories;

import br.com.allef.biblioteca.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    Optional<Usuario> findByIdAndAtivoIsTrue(Long aLong);

    List<Usuario> findAllByAtivoIsTrue();







}
