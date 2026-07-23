package br.com.edu.uninter.monitoria.controller;

import br.com.edu.uninter.monitoria.dto.MateriaRequest;
import br.com.edu.uninter.monitoria.dto.MateriaResponse;
import br.com.edu.uninter.monitoria.service.MateriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/materia")
@RequiredArgsConstructor
public class MateriaController {

    private final MateriaService materiaService;

    @GetMapping
    public ResponseEntity<List<MateriaResponse>> listar() {
        List<MateriaResponse> response = materiaService.listarTodos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaResponse> listarPorId(@PathVariable Long id) {
        MateriaResponse response = materiaService.listarPorId(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<MateriaResponse> criar(@Valid @RequestBody MateriaRequest materiaRequest) {
        MateriaResponse response = materiaService.criar(materiaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MateriaResponse> atualizar(@PathVariable Long id, @Valid @RequestBody MateriaRequest materiaRequest) {
        MateriaResponse response = materiaService.atualizar(id, materiaRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MateriaResponse> remover(@PathVariable Long id) {
        materiaService.remover(id);
        return ResponseEntity.noContent().build();
    }

}
