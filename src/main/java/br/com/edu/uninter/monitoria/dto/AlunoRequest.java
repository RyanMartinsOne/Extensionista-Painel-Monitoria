package br.com.edu.uninter.monitoria.dto;

import br.com.edu.uninter.monitoria.model.TipoAluno;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record AlunoRequest(
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        String telefone,

        @NotNull(message = "O tipo do aluno é obrigatório")
        TipoAluno tipo,

        @NotEmpty(message = "Informe ao menos uma matéria")
        Set<Long> materiaIds,

        Set<String> disponibilidade
) {}