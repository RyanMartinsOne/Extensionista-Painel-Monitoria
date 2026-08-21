package br.com.edu.uninter.monitoria.controller;

import br.com.edu.uninter.monitoria.dto.UsuarioRequest;
import br.com.edu.uninter.monitoria.dto.UsuarioResponse;
import br.com.edu.uninter.monitoria.mapper.UsuarioMapper;
import br.com.edu.uninter.monitoria.model.Usuario;
import br.com.edu.uninter.monitoria.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @GetMapping("/eu")
    public ResponseEntity<UsuarioResponse> eu(
            @AuthenticationPrincipal Usuario usuario
    ){
        System.out.println("Usuário autenticado: " + usuario);

        return ResponseEntity.ok(usuarioMapper.toDto(usuario));
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
