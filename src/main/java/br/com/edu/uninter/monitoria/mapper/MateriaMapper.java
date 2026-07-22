package br.com.edu.uninter.monitoria.mapper;

import br.com.edu.uninter.monitoria.dto.MateriaDTO;
import br.com.edu.uninter.monitoria.dto.MateriaResponse;
import br.com.edu.uninter.monitoria.model.Materia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MateriaMapper {

    MateriaResponse toDto(Materia materia);

    MateriaDTO toDtoSimples(Materia materia);

    @Mapping(target = "id", ignore = true)
    Materia toEntity (MateriaDTO materiaDTO);
}
