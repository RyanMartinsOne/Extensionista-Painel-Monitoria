package br.com.edu.uninter.monitoria.controller;

import br.com.edu.uninter.monitoria.dto.LoginRequest;
import br.com.edu.uninter.monitoria.dto.RegisterRequest;
import br.com.edu.uninter.monitoria.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.login(request, authenticationManager));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok().build();
    }
}
