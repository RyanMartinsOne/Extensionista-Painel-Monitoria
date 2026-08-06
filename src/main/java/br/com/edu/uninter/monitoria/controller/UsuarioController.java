package br.com.edu.uninter.monitoria.controller;

import br.com.edu.uninter.monitoria.dto.UsuarioRequest;
import br.com.edu.uninter.monitoria.dto.UsuarioResponse;
import br.com.edu.uninter.monitoria.model.Materia;
import br.com.edu.uninter.monitoria.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listar() {
        List<UsuarioResponse> response = usuarioService.listarTodos();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> listarPorId(@PathVariable Long id) {
        UsuarioResponse response = usuarioService.listarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/materia")
    public ResponseEntity<List<UsuarioResponse>> listarPorMateria(@RequestBody Materia materia) {
        List<UsuarioResponse> response = usuarioService.listarPorMateria(materia);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioRequest usuarioRequest) {
        UsuarioResponse response = usuarioService.atualizar(id, usuarioRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioResponse> remover(@PathVariable Long id) {
        usuarioService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
