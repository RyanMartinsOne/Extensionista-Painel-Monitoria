package br.com.edu.uninter.monitoria.dto;

import br.com.edu.uninter.monitoria.model.Materia;

public record UsuarioResponse(
        Long id,
        String nome,
        Materia materia
) {}
