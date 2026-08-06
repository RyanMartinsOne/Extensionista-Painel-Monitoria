package br.com.edu.uninter.monitoria.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotBlank(message = "A senha é obrigatória")
        String senha
) {}
