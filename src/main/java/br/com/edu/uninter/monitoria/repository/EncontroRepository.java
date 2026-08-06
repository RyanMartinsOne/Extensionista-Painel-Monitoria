package br.com.edu.uninter.monitoria.repository;

import br.com.edu.uninter.monitoria.model.Encontro;
import br.com.edu.uninter.monitoria.model.Materia;
import br.com.edu.uninter.monitoria.model.StatusEncontro;
import br.com.edu.uninter.monitoria.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EncontroRepository extends JpaRepository<Encontro, Long> {
    List<Encontro> findByStatus(StatusEncontro status);
    long countByStatus(StatusEncontro status);
    List<Encontro> findByMateria(Materia materia);
    List<Encontro> findByMonitor(Usuario usuario);
    List<Encontro> findByMonitor_NomeOrBeneficiado(String monitor, String beneficiado);

    boolean existsByMonitor_Id(Long id);
}
