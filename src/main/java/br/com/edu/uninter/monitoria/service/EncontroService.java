package br.com.edu.uninter.monitoria.service;

import br.com.edu.uninter.monitoria.dto.EncontroFilter;
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
    public List<EncontroResponse> listarDashboard(EncontroFilter filter) {
        List<Encontro> encontro = encontroRepository.findWithFilters(filter.status(), filter.materia());
        return encontro.stream()
                .map(encontroMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EncontroResponse> listar(Usuario  usuario) {
        List<Encontro> encontro = encontroRepository.findByMonitor(usuario);
        return encontro.stream()
                .map(encontroMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EncontroResponse> listarPorMonitor(Long id) {
        List<Encontro> encontro = encontroRepository.findByMonitor_Id(id);
        return encontro.stream()
                .map(encontroMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EncontroResponse> listarPorStatusEUsuario(StatusEncontro status, Usuario usuario) {
        List<Encontro> encontro = encontroRepository.findByStatusAndMonitor(status, usuario);

        return encontro
                .stream()
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
    public long quantidadePorStatus(StatusEncontro status) {
        return encontroRepository.countByStatus(status);
    }

    @Transactional
    public EncontroResponse criar(EncontroRequest request, Usuario monitorLogado) {
        Encontro encontro = encontroMapper.toEntity(request);
        encontro.setMonitor(monitorLogado);
        encontro.setStatus(StatusEncontro.AGENDADO);

        Encontro encontroNovo = encontroRepository.save(encontro);
        return encontroMapper.toDto(encontroNovo);
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

        Encontro encontroAtualizado = encontroRepository.save(encontro);
        return encontroMapper.toDto(encontroAtualizado);
    }

    public EncontroResponse atualizarStatus(Long id, StatusEncontro status) {
        Encontro encontro = encontroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não existe encontro com id: " + id));

        encontro.setStatus(status);
        Encontro encontroAtualizado = encontroRepository.save(encontro);
        return encontroMapper.toDto(encontroAtualizado);
    }

    @Transactional
    public void remover(Long id) {
        Encontro encontro = encontroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Não existe encontro com id: " + id
                ));

        encontroRepository.delete(encontro);
    }

}
