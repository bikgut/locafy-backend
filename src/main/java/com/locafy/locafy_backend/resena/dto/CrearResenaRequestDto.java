package com.locafy.locafy_backend.resena.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearResenaRequestDto {

    // Datos de la app para agregar una resena.
    @NotBlank(message = "El comentario es obligatorio")
    private String comentario;

    @Min(value = 1, message = "La calificacion debe ser mayor o igual a 1")
    @Max(value = 5, message = "La calificacion debe ser menor o igual a 5")
    private int calificacion;

    @NotNull(message = "El usuarioId es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El localId es obligatorio")
    private Long localId;
}
