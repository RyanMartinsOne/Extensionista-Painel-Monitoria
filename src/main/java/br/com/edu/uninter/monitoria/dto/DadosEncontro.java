package br.com.edu.uninter.monitoria.dto;

import br.com.edu.uninter.monitoria.model.Aluno;
import br.com.edu.uninter.monitoria.model.Materia;

public record DadosEncontro(
        Aluno monitor,
        Aluno beneficiado,
        Materia materia
) {}
