package com.locafy.locafy_backend.resena.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.locafy.locafy_backend.common.exception.GlobalExceptionHandler;
import com.locafy.locafy_backend.resena.dto.CrearResenaRequestDto;
import com.locafy.locafy_backend.resena.dto.ResenaResponseDto;
import com.locafy.locafy_backend.resena.service.ResenaService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@ExtendWith(MockitoExtension.class)
class ResenaControllerTest {

    @Mock
    private ResenaService resenaService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new ResenaController(resenaService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();
    }

    @Test
    void crearResenaDebeRetornar201() throws Exception {
        String request = """
                {
                  "comentario": "Muy buen lugar",
                  "calificacion": 5,
                  "usuarioId": 1,
                  "localId": 10
                }
                """;

        ResenaResponseDto response = new ResenaResponseDto();
        response.setId(100L);
        response.setComentario("Muy buen lugar");
        response.setCalificacion(5);
        response.setUsuarioId(1L);
        response.setUsuarioNombre("Sebastian");
        response.setLocalId(10L);
        response.setLocalNombre("Cafe Centro");

        when(resenaService.crearResena(any(CrearResenaRequestDto.class))).thenReturn(response);

        mockMvc.perform(post("/api/resenas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.comentario").value("Muy buen lugar"))
                .andExpect(jsonPath("$.usuarioId").value(1))
                .andExpect(jsonPath("$.localId").value(10));
    }

    @Test
    void obtenerResenasPorLocalDebeRetornar200() throws Exception {
        ResenaResponseDto response = new ResenaResponseDto();
        response.setId(100L);
        response.setComentario("Muy buen lugar");
        response.setCalificacion(5);
        response.setUsuarioId(1L);
        response.setUsuarioNombre("Sebastian");
        response.setLocalId(10L);
        response.setLocalNombre("Cafe Centro");

        when(resenaService.obtenerResenasPorLocal(10L)).thenReturn(List.of(response));

        mockMvc.perform(get("/api/resenas/local/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(100))
                .andExpect(jsonPath("$[0].localNombre").value("Cafe Centro"));
    }

    @Test
    void crearResenaDebeRetornar400CuandoElBodyEsInvalido() throws Exception {
        String bodyInvalido = """
                {
                  "comentario": "",
                  "calificacion": 0,
                  "usuarioId": null,
                  "localId": null
                }
                """;

        mockMvc.perform(post("/api/resenas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyInvalido))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }
}
