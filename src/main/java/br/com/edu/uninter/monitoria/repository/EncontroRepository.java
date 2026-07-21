package br.com.edu.uninter.monitoria.repository;

import br.com.edu.uninter.monitoria.model.Encontro;
import br.com.edu.uninter.monitoria.model.StatusEncontro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EncontroRepository extends JpaRepository<Encontro, Long> {
    List<Encontro> findByStatus(StatusEncontro status);
    Long countByStatus(StatusEncontro status);

}
