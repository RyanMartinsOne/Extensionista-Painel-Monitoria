package br.com.edu.uninter.monitoria.controller;

import br.com.edu.uninter.monitoria.dto.EncontroRequest;
import br.com.edu.uninter.monitoria.dto.EncontroResponse;
import br.com.edu.uninter.monitoria.model.Materia;
import br.com.edu.uninter.monitoria.model.StatusEncontro;
import br.com.edu.uninter.monitoria.model.Usuario;
import br.com.edu.uninter.monitoria.service.EncontroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/encontros")
@RequiredArgsConstructor
public class EncontroController {

    private final EncontroService encontroService;


    @GetMapping
    public ResponseEntity<List<EncontroResponse>> listarPorMonitor(@AuthenticationPrincipal Usuario usuario){
        List<EncontroResponse> response = encontroService.listarPorMonitor(usuario);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EncontroResponse>> listarTodos(
            @RequestParam(required = false) StatusEncontro status
    ) {
        if (status != null) {
            List<EncontroResponse> response = encontroService.listarPorStatus(status);
            return ResponseEntity.ok(response);
        }
        List<EncontroResponse> response = encontroService.listarTodos();

       return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EncontroResponse> listarPorId(@PathVariable Long id){
        EncontroResponse encontroResponse = encontroService.listarPorId(id);
        return ResponseEntity.ok(encontroResponse);
    }

    @GetMapping("/materia")
    public ResponseEntity<List<EncontroResponse>> listarPorMateria(@RequestParam Materia materia){
        List<EncontroResponse> response = encontroService.listarPorMateria(materia);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/aluno")
    public ResponseEntity<List<EncontroResponse>> listarPorNomeAluno(@RequestParam String nomeAluno){
        List<EncontroResponse> encontroResponse = encontroService.listarPorNomeAluno(nomeAluno);
        return ResponseEntity.ok(encontroResponse);
    }

    @GetMapping("/status")
    public ResponseEntity<Long> quantidadeStatus(@RequestParam StatusEncontro status){
        long response = encontroService.quantidadePorStatus(status);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<EncontroResponse> criar(
            @Valid @RequestBody EncontroRequest encontroRequest,
            @AuthenticationPrincipal Usuario monitor
    ){
        EncontroResponse response = encontroService.criar(encontroRequest, monitor);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EncontroResponse> atualizar(@PathVariable Long id, @Valid @RequestBody EncontroRequest encontroRequest){
        EncontroResponse response = encontroService.atualizar(id, encontroRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<EncontroResponse> atualizarStatus(@PathVariable Long id, @Valid @RequestBody StatusEncontro status){
        EncontroResponse response = encontroService.atualizarStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id){
        encontroService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
