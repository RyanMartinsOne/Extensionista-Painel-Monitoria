package br.com.edu.uninter.monitoria.repository;

import br.com.edu.uninter.monitoria.model.MonitoriaImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonitoriaImageRepository extends JpaRepository<MonitoriaImage, Long> {

    Optional<MonitoriaImage> findByEncontroId(Long encontroId);
}
