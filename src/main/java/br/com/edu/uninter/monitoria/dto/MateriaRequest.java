package br.com.edu.uninter.monitoria.dto;

import jakarta.validation.constraints.NotBlank;

public record MateriaRequest(
        @NotBlank(message = "O nome da matéria é obrigatório")
        String nome
) {}
