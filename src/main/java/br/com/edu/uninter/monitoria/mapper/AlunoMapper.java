package br.com.edu.uninter.monitoria.mapper;

import br.com.edu.uninter.monitoria.dto.AlunoRequest;
import br.com.edu.uninter.monitoria.dto.AlunoResponse;
import br.com.edu.uninter.monitoria.model.Aluno;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AlunoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "materias", ignore = true)
    Aluno toEntity(AlunoRequest alunoRequest);

    AlunoResponse toDto(Aluno aluno);
}
