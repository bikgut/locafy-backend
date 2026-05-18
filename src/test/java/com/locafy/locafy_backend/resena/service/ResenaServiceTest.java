package com.locafy.locafy_backend.resena.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.locafy.locafy_backend.common.exception.ResourceNotFoundException;
import com.locafy.locafy_backend.local.repository.LocalRepository;
import com.locafy.locafy_backend.model.Local;
import com.locafy.locafy_backend.model.Resena;
import com.locafy.locafy_backend.model.Usuario;
import com.locafy.locafy_backend.resena.dto.CrearResenaRequestDto;
import com.locafy.locafy_backend.resena.dto.ResenaResponseDto;
import com.locafy.locafy_backend.resena.repository.ResenaRepository;
import com.locafy.locafy_backend.usuario.repository.UsuarioRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ResenaServiceTest {

    @Mock
    private ResenaRepository resenaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private LocalRepository localRepository;

    @InjectMocks
    private ResenaService resenaService;

    @Test
    void crearResenaDebeRetornarResenaResponseDto() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Sebastian");

        Local local = new Local();
        local.setId(10L);
        local.setNombre("Cafe Centro");

        CrearResenaRequestDto request = new CrearResenaRequestDto();
        request.setComentario("Muy buen lugar");
        request.setCalificacion(5);
        request.setUsuarioId(1L);
        request.setLocalId(10L);

        Resena resenaGuardada = new Resena();
        resenaGuardada.setId(100L);
        resenaGuardada.setComentario("Muy buen lugar");
        resenaGuardada.setCalificacion(5);
        resenaGuardada.setUsuario(usuario);
        resenaGuardada.setLocal(local);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(localRepository.findById(10L)).thenReturn(Optional.of(local));
        when(resenaRepository.save(any(Resena.class))).thenReturn(resenaGuardada);

        ResenaResponseDto response = resenaService.crearResena(request);

        assertEquals(100L, response.getId());
        assertEquals("Muy buen lugar", response.getComentario());
        assertEquals(5, response.getCalificacion());
        assertEquals(1L, response.getUsuarioId());
        assertEquals(10L, response.getLocalId());
    }

    @Test
    void obtenerResenasPorLocalDebeLanzarNotFoundCuandoLocalNoExiste() {
        when(localRepository.existsById(10L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> resenaService.obtenerResenasPorLocal(10L));
    }
}
