package br.com.edu.uninter.monitoria.repository;

import br.com.edu.uninter.monitoria.model.Aluno;
import br.com.edu.uninter.monitoria.model.TipoAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    List<Aluno> findByTipo (TipoAluno tipo);

    // Distinc evita repetir aluno mesmo se ele tiver várias materias
    @Query("SELECT DISTINCT a FROM Aluno a LEFT JOIN FETCH a.materias WHERE a.tipo = :tipo")
    List<Aluno> findByTipoWithMaterias(@Param("tipo") TipoAluno tipo);
}
