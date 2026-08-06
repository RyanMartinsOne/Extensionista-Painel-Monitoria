package br.com.edu.uninter.monitoria.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record EncontroRequest(
        @NotNull(message = "O nome do aluno beneficiado é obrigatório")
        String beneficiado,

        @NotNull(message = "A matéria é obrigatória")
        Materia materia,

        @NotNull(message = "O id da matéria é obrigatório")
        Long materiaId,

        @NotNull(message = "A data e hora são obrigatórias")
        LocalDateTime dataHora,

        String observacoes
) {}
