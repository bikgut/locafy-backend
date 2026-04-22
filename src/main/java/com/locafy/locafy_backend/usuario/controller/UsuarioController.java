package com.locafy.locafy_backend.usuario.controller;

import com.locafy.locafy_backend.usuario.dto.UsuarioResponseDto;
import com.locafy.locafy_backend.usuario.service.UsuarioService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    // El controller solo delega y expone endpoints del modulo usuario.
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> obtenerPerfil(@PathVariable Long id) {
        // Devuelve el perfil segun el id recibido por path.
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> listarUsuarios() {
        // Lista todos los usuarios disponibles en la base.
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        // Si la eliminacion se completa, se responde sin contenido.
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
