package com.locafy.locafy_backend.usuario.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.locafy.locafy_backend.common.exception.ConflictException;
import com.locafy.locafy_backend.model.Usuario;
import com.locafy.locafy_backend.resena.repository.ResenaRepository;
import com.locafy.locafy_backend.usuario.dto.UsuarioResponseDto;
import com.locafy.locafy_backend.usuario.repository.UsuarioRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ResenaRepository resenaRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void obtenerPorIdDebeRetornarUsuarioResponseDto() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Sebastian");
        usuario.setEmail("sebastian@locafy.com");
        usuario.setPassword("123456");
        usuario.setRol("USER");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        UsuarioResponseDto response = usuarioService.obtenerPorId(1L);

        assertEquals(1L, response.getId());
        assertEquals("Sebastian", response.getNombre());
        assertEquals("sebastian@locafy.com", response.getEmail());
        assertEquals("USER", response.getRol());
    }

    @Test
    void eliminarUsuarioDebeLanzarConflictCuandoTieneResenas() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);
        when(resenaRepository.countByUsuarioId(1L)).thenReturn(2L);

        assertThrows(ConflictException.class, () -> usuarioService.eliminarUsuario(1L));

        verify(usuarioRepository, never()).deleteById(1L);
    }
}
