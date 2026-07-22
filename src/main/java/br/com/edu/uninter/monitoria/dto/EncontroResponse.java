package br.com.edu.uninter.monitoria.dto;

import br.com.edu.uninter.monitoria.model.StatusEncontro;

import java.time.LocalDateTime;

public record EncontroResponse(
        Long id,
        AlunoResponse monitor,
        AlunoResponse beneficiado,
        MateriaResponse materia,
        StatusEncontro status,
        LocalDateTime dataHora,
        String observacoes
) {}
