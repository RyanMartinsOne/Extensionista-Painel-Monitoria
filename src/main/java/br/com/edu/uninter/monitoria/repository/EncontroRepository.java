package br.com.edu.uninter.monitoria.repository;

import br.com.edu.uninter.monitoria.model.Encontro;
import br.com.edu.uninter.monitoria.model.Materia;
import br.com.edu.uninter.monitoria.model.StatusEncontro;
import br.com.edu.uninter.monitoria.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EncontroRepository extends JpaRepository<Encontro, Long> {
    List<Encontro> findByStatus(StatusEncontro status);
    List<Encontro>  findByStatusAndMonitor(StatusEncontro status, Usuario usuario);
    long countByStatus(StatusEncontro status);
    List<Encontro> findByMonitor(Usuario usuario);

    boolean existsByMonitor_Id(Long id);

    List<Encontro> findByMonitor_Id(Long monitorId);

    // Se não for passado um filtro seu valor fica nulo
    @Query("""
        SELECT e
        FROM Encontro e
        WHERE (:status IS NULL OR e.status = :status)
            AND (:materia IS NULL OR e.materia = :materia)
    """)
    List<Encontro> findWithFilters(
            StatusEncontro status,
            Materia materia
    );
}
