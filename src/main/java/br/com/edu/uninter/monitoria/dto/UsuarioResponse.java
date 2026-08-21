package br.com.edu.uninter.monitoria.dto;

import br.com.edu.uninter.monitoria.model.Materia;
import br.com.edu.uninter.monitoria.model.TipoUsuario;

public record UsuarioResponse(
        Long id,
        String nome,
        Materia materia,
        TipoUsuario tipo
) {}
