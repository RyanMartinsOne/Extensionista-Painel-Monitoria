package br.com.edu.uninter.monitoria.mapper;

import br.com.edu.uninter.monitoria.dto.EncontroRequest;
import br.com.edu.uninter.monitoria.dto.EncontroResponse;
import br.com.edu.uninter.monitoria.model.Encontro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {AlunoMapper.class, MateriaMapper.class}
)
public interface EncontroMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "monitor", ignore = true)
    @Mapping(target = "status", ignore = true)
    Encontro toEntity(EncontroRequest request);

    EncontroResponse toDto(Encontro encontro);
}