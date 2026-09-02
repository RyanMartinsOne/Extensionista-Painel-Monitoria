package br.com.edu.uninter.monitoria.controller;

import br.com.edu.uninter.monitoria.dto.EncontroFilter;
import br.com.edu.uninter.monitoria.dto.EncontroResponse;
import br.com.edu.uninter.monitoria.dto.UsuarioResponse;
import br.com.edu.uninter.monitoria.model.Materia;
import br.com.edu.uninter.monitoria.service.EncontroService;
import br.com.edu.uninter.monitoria.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final EncontroService encontroService;
    private final UsuarioService usuarioService;

    @GetMapping("/encontros")
    public ResponseEntity<List<EncontroResponse>> listarEncontros(
            @ModelAttribute EncontroFilter filter
    ) {
        List<EncontroResponse> response = encontroService.listarDashboard(filter);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/encontros/{id}")
    public ResponseEntity<List<EncontroResponse>> listarEncontrosPorMonitor(@PathVariable Long id) {
        List<EncontroResponse> response = encontroService.listarPorMonitor(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios(
            @RequestParam(required = false) Materia materia
            ) {
        List<UsuarioResponse> response = usuarioService.listarDashboard(materia);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResponse> listarUsuarioPorId(@PathVariable Long id) {
        UsuarioResponse response = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

}
