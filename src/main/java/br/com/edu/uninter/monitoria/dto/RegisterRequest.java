package br.com.edu.uninter.monitoria.dto;

import br.com.edu.uninter.monitoria.model.Materia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotNull(message = "A matéria é obrigatória")
        Materia materia,
        @NotBlank(message = "A senha é obrigatória")
        String senha
) {}
