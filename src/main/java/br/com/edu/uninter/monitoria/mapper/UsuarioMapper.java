package br.com.edu.uninter.monitoria.mapper;

import br.com.edu.uninter.monitoria.dto.UsuarioResponse;
import br.com.edu.uninter.monitoria.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioResponse toDto(Usuario usuario);
}
