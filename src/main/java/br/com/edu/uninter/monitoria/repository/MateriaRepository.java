package br.com.edu.uninter.monitoria.repository;


import br.com.edu.uninter.monitoria.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MateriaRepository extends JpaRepository<Materia, Long> {
}
