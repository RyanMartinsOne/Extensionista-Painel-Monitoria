package br.com.edu.uninter.monitoria.dto;

import br.com.edu.uninter.monitoria.model.Materia;
import br.com.edu.uninter.monitoria.model.StatusEncontro;

import java.time.LocalDateTime;

public record EncontroResponse(
        Long id,
        UsuarioResponse monitor,
        String beneficiado,
        Materia materia,
        String assunto,
        String telefone,
        StatusEncontro status,
        LocalDateTime dataHora,
        String observacoes
) {}
