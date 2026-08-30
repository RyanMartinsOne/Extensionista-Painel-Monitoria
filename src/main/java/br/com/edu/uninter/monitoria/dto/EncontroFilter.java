package br.com.edu.uninter.monitoria.dto;

import br.com.edu.uninter.monitoria.model.Materia;
import br.com.edu.uninter.monitoria.model.StatusEncontro;

public record EncontroFilter(
        StatusEncontro status,
        Materia materia
) {}
