package com.locafy.locafy_backend.usuario.controller;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.locafy.locafy_backend.common.exception.ConflictException;
import com.locafy.locafy_backend.common.exception.GlobalExceptionHandler;
import com.locafy.locafy_backend.usuario.dto.UsuarioResponseDto;
import com.locafy.locafy_backend.usuario.service.UsuarioService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UsuarioController(usuarioService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void obtenerPerfilDebeRetornar200() throws Exception {
        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setId(1L);
        dto.setNombre("Sebastian");
        dto.setEmail("sebastian@locafy.com");
        dto.setRol("USER");

        when(usuarioService.obtenerPorId(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Sebastian"))
                .andExpect(jsonPath("$.email").value("sebastian@locafy.com"))
                .andExpect(jsonPath("$.rol").value("USER"));
    }

    @Test
    void listarUsuariosDebeRetornar200() throws Exception {
        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setId(1L);
        dto.setNombre("Sebastian");
        dto.setEmail("sebastian@locafy.com");
        dto.setRol("USER");

        when(usuarioService.listarUsuarios()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Sebastian"));
    }

    @Test
    void eliminarUsuarioDebeRetornar409CuandoHayConflicto() throws Exception {
        doThrow(new ConflictException("No se puede eliminar el usuario porque tiene resenas asociadas"))
                .when(usuarioService).eliminarUsuario(1L);

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("No se puede eliminar el usuario porque tiene resenas asociadas"));
    }
}
