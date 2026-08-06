package br.com.edu.uninter.monitoria.service;

import br.com.edu.uninter.monitoria.dto.UsuarioRequest;
import br.com.edu.uninter.monitoria.dto.UsuarioResponse;
import br.com.edu.uninter.monitoria.mapper.UsuarioMapper;
import br.com.edu.uninter.monitoria.model.Materia;
import br.com.edu.uninter.monitoria.model.Usuario;
import br.com.edu.uninter.monitoria.repository.UsuarioRepository;
import br.com.edu.uninter.monitoria.repository.EncontroRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final EncontroRepository encontroRepository;

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarPorMateria(Materia materia) {
        List<Usuario> usuarios = usuarioRepository.findByMateria(materia);

        return usuarios.stream()
                .map(usuarioMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream()
                .map(usuarioMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponse listarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não existe usuário com id: " + id));

        return usuarioMapper.toDto(usuario);
    }

    @Transactional
    public UsuarioResponse atualizar(Long id, UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não existe usuário com id: " + id));

        usuario.setNome(request.nome());
        usuario.setMateria(request.materia());

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);

        return usuarioMapper.toDto(usuarioAtualizado);
    }

    @Transactional
    public void remover(Long id) {

        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Não existe aluno com id: " + id);
        } else if (encontroRepository.existsByMonitor_Id(id)) {
            throw new IllegalArgumentException("Não é possível excluir, este aluno participa " +
                    "de um ou mais encontros registrado.");
        }

        usuarioRepository.deleteById(id);
    }
}
