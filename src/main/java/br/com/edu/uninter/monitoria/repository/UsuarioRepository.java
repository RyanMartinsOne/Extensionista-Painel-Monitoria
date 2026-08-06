package br.com.edu.uninter.monitoria.repository;

import br.com.edu.uninter.monitoria.model.Materia;
import br.com.edu.uninter.monitoria.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNome(String nome);

    List<Usuario> findByMateria(Materia materia);

}
