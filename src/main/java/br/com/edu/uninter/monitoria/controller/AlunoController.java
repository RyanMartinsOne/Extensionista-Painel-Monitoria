package br.com.edu.uninter.monitoria.controller;

import br.com.edu.uninter.monitoria.dto.AlunoRequest;
import br.com.edu.uninter.monitoria.dto.AlunoResponse;
import br.com.edu.uninter.monitoria.model.TipoAluno;
import br.com.edu.uninter.monitoria.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aluno")
public class AlunoController {

    private final AlunoService alunoService;

    @GetMapping
    public ResponseEntity<List<AlunoResponse>> listar(
            @RequestParam(required = false) TipoAluno tipo
    ) {
        if (tipo != null) {
            List<AlunoResponse> response = alunoService.listarPorTipo(tipo);
            return ResponseEntity.ok(response);
        }
        List<AlunoResponse> response = alunoService.listarTodos();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponse> listarPorId(@PathVariable Long id) {
        AlunoResponse response = alunoService.listarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/materia/{id}")
    public ResponseEntity<List<AlunoResponse>> listarPorIdMateria(@PathVariable Long id) {
        List<AlunoResponse> response = alunoService.listarPorMateria(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<AlunoResponse> criar(@Valid @RequestBody AlunoRequest alunoRequest) {
        AlunoResponse response = alunoService.criar(alunoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponse> atualizar(@PathVariable Long id, @Valid @RequestBody AlunoRequest alunoRequest) {
        AlunoResponse response = alunoService.atualizar(id, alunoRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AlunoResponse> remover(@PathVariable Long id) {
        alunoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
