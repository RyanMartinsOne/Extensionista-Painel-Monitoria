package br.com.edu.uninter.monitoria.mapper;

import br.com.edu.uninter.monitoria.dto.MateriaRequest;
import br.com.edu.uninter.monitoria.dto.MateriaResponse;
import br.com.edu.uninter.monitoria.model.Materia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MateriaMapper {

    MateriaResponse toDto(Materia materia);

    @Mapping(target = "id", ignore = true)
    Materia toEntity (MateriaRequest materiaRequest);
}
