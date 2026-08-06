package br.com.edu.uninter.monitoria.service;

import br.com.edu.uninter.monitoria.dto.EncontroRequest;
import br.com.edu.uninter.monitoria.dto.EncontroResponse;
import br.com.edu.uninter.monitoria.mapper.EncontroMapper;
import br.com.edu.uninter.monitoria.model.*;
import br.com.edu.uninter.monitoria.repository.EncontroRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EncontroService {

    private final EncontroRepository encontroRepository;
    private final EncontroMapper encontroMapper;

    @Transactional(readOnly = true)
    public List<EncontroResponse> listarTodos() {
        List<Encontro> encontro = encontroRepository.findAll();
        return encontro.stream()
                .map(encontroMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EncontroResponse> listarPorMonitor(Usuario  usuario) {
        List<Encontro> encontro = encontroRepository.findByMonitor(usuario);
        return encontro.stream()
                .map(encontroMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public EncontroResponse listarPorId(Long id) {
        Encontro encontro = encontroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Não existe encontro com id " + id
                ));

        return encontroMapper.toDto(encontro);
    }

    @Transactional(readOnly = true)
    public List<EncontroResponse> listarPorMateria(Materia materia) {
        List<Encontro> encontro = encontroRepository.findByMateria(materia);

        return encontro.stream()
                .map(encontroMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EncontroResponse> listarPorNomeAluno(String nome) {
        List<Encontro> encontro = encontroRepository.findByMonitor_NomeOrBeneficiado(nome, nome);
        return encontro.stream()
                .map(encontroMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EncontroResponse> listarPorStatus(StatusEncontro status) {
        List<Encontro> encontro = encontroRepository.findByStatus(status);

        return encontro
                .stream()
                .map(encontroMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public int quantidadePorStatus(StatusEncontro status) {
        return encontroRepository.countByStatus(status);
    }

    @Transactional
    public EncontroResponse criar(EncontroRequest request) {
        DadosEncontro dados = validarDadosEncontro(request);

        Encontro encontro = encontroMapper.toEntity(request);
        encontro.setMonitor(dados.monitor());
        encontro.setBeneficiado(dados.beneficiado());
        encontro.setMateria(dados.materia());
        encontro.setStatus(StatusEncontro.AGENDADO);

        return encontroMapper.toDto(encontroRepository.save(encontro));
    }

    @Transactional
    public EncontroResponse atualizar(Long id, EncontroRequest request) {
        Encontro encontro = encontroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não existe encontro com id: " + id));

        encontro.setBeneficiado(request.beneficiado());
        encontro.setMateria(request.materia());
        encontro.setAssunto(request.assunto());
        encontro.setTelefone(request.telefone());
        encontro.setDataHora(request.dataHora());
        encontro.setObservacoes(request.observacoes());

        encontro.setMonitor(dados.monitor());
        encontro.setBeneficiado(dados.beneficiado());
        encontro.setMateria(dados.materia());

        return encontroMapper.toDto(encontro);
    }

    @Transactional
    public void remover(Long id) {
        Encontro encontro = encontroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Não existe encontro com id: " + id
                ));

        encontroRepository.delete(encontro);
    }

    private DadosEncontro validarDadosEncontro(EncontroRequest request) {
        Aluno monitor = alunoRepository.findById(request.monitorId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Não existe aluno monitor com id: " + request.monitorId()
                ));

        Aluno beneficiado = alunoRepository.findById(request.beneficiadoId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Não existe aluno beneficiado com id: " + request.beneficiadoId()
                ));

        Materia materia = materiaRepository.findById(request.materiaId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Não existe matéria com id: " + request.materiaId()
                ));

        if (monitor.getId().equals(beneficiado.getId())) {
            throw new IllegalArgumentException(
                    "O aluno beneficiado e o monitor devem ser pessoas diferentes"
            );
        }

        if (monitor.getTipo() != TipoAluno.MONITOR) {
            throw new IllegalArgumentException(
                    "O aluno informado como monitor não possui tipo MONITOR"
            );
        }

        if (!monitor.getMaterias().contains(materia)) {
            throw new IllegalArgumentException(
                    "O monitor não está vinculado à matéria " + materia.getNome()
            );
        }

        return new DadosEncontro(monitor, beneficiado, materia);
    }
}
