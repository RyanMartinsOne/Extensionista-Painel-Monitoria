package br.com.edu.uninter.monitoria.service;

import br.com.edu.uninter.monitoria.dto.AlunoRequest;
import br.com.edu.uninter.monitoria.dto.AlunoResponse;
import br.com.edu.uninter.monitoria.mapper.AlunoMapper;
import br.com.edu.uninter.monitoria.model.Aluno;
import br.com.edu.uninter.monitoria.model.Materia;
import br.com.edu.uninter.monitoria.model.TipoAluno;
import br.com.edu.uninter.monitoria.repository.AlunoRepository;
import br.com.edu.uninter.monitoria.repository.EncontroRepository;
import br.com.edu.uninter.monitoria.repository.MateriaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final AlunoMapper alunoMapper;
    private final MateriaRepository materiaRepository;
    private final EncontroRepository encontroRepository;

    @Transactional(readOnly = true)
    public List<AlunoResponse> listarPorTipo(TipoAluno tipo) {

        List<Aluno> alunos = (tipo == TipoAluno.MONITOR)
                ? alunoRepository.findByTipoWithMaterias(tipo)
                : alunoRepository.findByTipo(tipo);

        return alunos.stream()
                .map(alunoMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AlunoResponse> listarPorMateria(Long materiaId) {
        List<Aluno> alunos = alunoRepository.findByMateriaId(materiaId);

        return alunos.stream()
                .map(alunoMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AlunoResponse> listarTodos() {
        List<Aluno> alunos = alunoRepository.findAll();

        return alunos.stream()
                .map(alunoMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public AlunoResponse listarPorId(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não existe aluno com id: " + id));

        return alunoMapper.toDto(aluno);
    }

    @Transactional
    public AlunoResponse criar(AlunoRequest request) {
        Aluno aluno = alunoMapper.toEntity(request);

        vincularMaterias(aluno, request.materiaIds());

        Aluno alunoSalvo = alunoRepository.save(aluno);

        return alunoMapper.toDto(alunoSalvo);
    }

    @Transactional
    public AlunoResponse atualizar(Long id, AlunoRequest request) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não existe aluno com id: " + id));

        aluno.setNome(request.nome());
        aluno.setTelefone(request.telefone());
        aluno.setTipo(request.tipo());
        aluno.setDisponibilidade(request.disponibilidade());

        vincularMaterias(aluno, request.materiaIds());

        Aluno alunoAtualizado = alunoRepository.save(aluno);

        return alunoMapper.toDto(alunoAtualizado);
    }

    @Transactional
    public void remover(Long id) {

        if (!alunoRepository.existsById(id)) {
            throw new EntityNotFoundException("Não existe aluno com id: " + id);
        }
        else if (encontroRepository.existsByMonitorIdOrBeneficiadoId(id, id)) {
            throw new IllegalArgumentException("Não é possível excluir, este aluno participa " +
                    "de um ou mais encontros registrado.");
        }

        alunoRepository.deleteById(id);
    }

    private void vincularMaterias(Aluno aluno, Set<Long> materiaIds) {
        if (aluno.getMaterias() == null) {
            aluno.setMaterias(new HashSet<>());
        } else {
            aluno.getMaterias().clear();
        }

        if (materiaIds == null || materiaIds.isEmpty()) {
            return;
        }

        List<Materia> materiasEncontradas = materiaRepository.findAllById(materiaIds);

        if (materiasEncontradas.size() != materiaIds.size()) {
            throw new EntityNotFoundException("Uma ou mais matérias informadas não foram encontradas!");
        }

        // Altera apenas o conteúdo da coleção, evitando criar uma nova
        aluno.getMaterias().addAll(materiasEncontradas);
    }
}
