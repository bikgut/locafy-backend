package com.locafy.locafy_backend.usuario.dto;

import com.locafy.locafy_backend.model.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDto {

    // Expone los datos necesarios para responder al cliente.
    private Long id;
    private String nombre;
    private String email;
    private String rol;

    // Conversion simple desde la entidad para no devolver el password.
    public static UsuarioResponseDto fromEntity(Usuario usuario) {
        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setRol(usuario.getRol());
        return dto;
    }

}
