package com.locafy.locafy_backend.resena.controller;

import com.locafy.locafy_backend.resena.dto.CrearResenaRequestDto;
import com.locafy.locafy_backend.resena.dto.ResenaResponseDto;
import com.locafy.locafy_backend.resena.service.ResenaService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController {

    // Punto de entrada REST para operaciones relacionadas con resenas.
    private final ResenaService resenaService;

    public ResenaController(ResenaService resenaService) {
        this.resenaService = resenaService;
    }

    @PostMapping
    public ResponseEntity<ResenaResponseDto> crearResena(@Valid @RequestBody CrearResenaRequestDto request) {
        // Se valida el body antes de delegar la creacion.
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(resenaService.crearResena(request));
    }

    @GetMapping("/local/{localId}")
    public ResponseEntity<List<ResenaResponseDto>> obtenerResenasPorLocal(@PathVariable Long localId) {
        // Obtiene todas las resenas asociadas a un local especifico.
        return ResponseEntity.ok(resenaService.obtenerResenasPorLocal(localId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ResenaResponseDto>> obtenerResenasPorUsuario(@PathVariable Long usuarioId) {
        // Obtiene las resenas publicadas por un usuario.
        return ResponseEntity.ok(resenaService.obtenerResenasPorUsuario(usuarioId));
    }
}
