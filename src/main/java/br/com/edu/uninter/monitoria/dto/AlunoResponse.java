package br.com.edu.uninter.monitoria.dto;

import br.com.edu.uninter.monitoria.model.TipoAluno;

import java.util.Set;

public record AlunoResponse(
        Long id,
        String nome,
        String telefone,
        TipoAluno tipo,
        Set<MateriaResponse> materias,
        Set<String> disponibilidade
) {}
