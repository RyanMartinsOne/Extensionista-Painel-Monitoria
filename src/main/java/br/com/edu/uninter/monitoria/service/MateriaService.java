package br.com.edu.uninter.monitoria.service;

import br.com.edu.uninter.monitoria.dto.MateriaRequest;
import br.com.edu.uninter.monitoria.dto.MateriaResponse;
import br.com.edu.uninter.monitoria.mapper.MateriaMapper;
import br.com.edu.uninter.monitoria.model.Materia;
import br.com.edu.uninter.monitoria.repository.AlunoRepository;
import br.com.edu.uninter.monitoria.repository.EncontroRepository;
import br.com.edu.uninter.monitoria.repository.MateriaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MateriaService {

    private final MateriaRepository materiaRepository;
    private final EncontroRepository encontroRepository;
    private final AlunoRepository alunoRepository;
    private final MateriaMapper materiaMapper;

    @Transactional(readOnly = true)
    public MateriaResponse listarPorId(Long id) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não existe materia com id: " + id));

        return materiaMapper.toDto(materia);
    }

    @Transactional(readOnly = true)
    public List<MateriaResponse> listarTodos() {
        List<Materia> materias = materiaRepository.findAll();

        return materias.stream()
                .map(materiaMapper::toDto)
                .toList();
    }

    @Transactional
    public MateriaResponse criarMateria(MateriaRequest materiaRequest) {
        Materia materia = materiaMapper.toEntity(materiaRequest);
        materiaRepository.save(materia);
        return materiaMapper.toDto(materia);
    }

    @Transactional
    public MateriaResponse atualizarMateria(Long id, MateriaRequest materiaRequest) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não existe matéria com id: " + id));

        materia.setNome(materiaRequest.nome());
        materiaRepository.save(materia);
        return materiaMapper.toDto(materia);
    }

    @Transactional
    public void removerMateria(Long id) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não existe matéria com id: " + id));

        if (encontroRepository.existsByMateriaId(id)) {
            throw new IllegalArgumentException("Não é possível excluir, esta matéria está em um ou mais encontros registrados.");
        }

        if (alunoRepository.existsByMaterias_Id(id)) {
            throw new IllegalStateException(
                    "Não é possível excluir: existem alunos vinculados a esta matéria."
            );
        }

        materiaRepository.delete(materia);
    }
}
