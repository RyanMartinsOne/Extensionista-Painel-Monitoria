package br.com.edu.uninter.monitoria.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record EncontroRequest(
        @NotNull(message = "O id do monitor é obrigatório")
        Long monitorId,

        @NotNull(message = "O id do aluno beneficiado é obrigatório")
        Long beneficiadoId,

        @NotNull(message = "O id da matéria é obrigatório")
        Long materiaId,

        @NotNull(message = "A data e hora são obrigatórias")
        LocalDateTime dataHora,

        String observacoes
) {}
