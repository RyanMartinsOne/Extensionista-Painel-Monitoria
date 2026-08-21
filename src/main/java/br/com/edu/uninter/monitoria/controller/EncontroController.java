package br.com.edu.uninter.monitoria.controller;

import br.com.edu.uninter.monitoria.dto.EncontroRequest;
import br.com.edu.uninter.monitoria.dto.EncontroResponse;
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
    public ResponseEntity<List<EncontroResponse>> listarPorMonitor(
            @AuthenticationPrincipal Usuario usuario,
            @RequestParam(required = false) StatusEncontro status
    ){
        if (status != null) {
            List<EncontroResponse> response = encontroService.listarPorStatusEUsuario(status, usuario);
            return ResponseEntity.ok(response);
        }

        List<EncontroResponse> response = encontroService.listar(usuario);
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

    @PatchMapping("/status/{id}")
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
