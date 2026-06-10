package com.locafy.locafy_backend.usuario.dto;

import com.locafy.locafy_backend.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequestDto {
    private String nombre;
    private String email;
    private String password;
    private Rol rol;
}
