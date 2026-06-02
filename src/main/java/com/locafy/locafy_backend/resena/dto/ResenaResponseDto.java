package com.locafy.locafy_backend.resena.dto;

import com.locafy.locafy_backend.model.Resena;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ResenaResponseDto {

    // La respuesta ya sale lista para consumir desde la app movil.
    private Long id;
    private String comentario;
    private int calificacion;
    private Long usuarioId;
    private String usuarioNombre;
    private Long localId;
    private String localNombre;

    // Se arma un dto plano para evitar exponer directamente la entidad.
    public static ResenaResponseDto fromEntity(Resena resena) {
        ResenaResponseDto dto = new ResenaResponseDto();
        dto.setId(resena.getId());
        dto.setComentario(resena.getComentario());
        dto.setCalificacion(resena.getCalificacion());
        dto.setUsuarioId(resena.getUsuario().getId());
        dto.setUsuarioNombre(resena.getUsuario().getNombre());
        dto.setLocalId(resena.getLocal().getId());
        dto.setLocalNombre(resena.getLocal().getNombre());
        return dto;
    }

}
