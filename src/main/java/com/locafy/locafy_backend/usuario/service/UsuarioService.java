package com.locafy.locafy_backend.usuario.service;

import com.locafy.locafy_backend.common.exception.ConflictException;
import com.locafy.locafy_backend.common.exception.ResourceNotFoundException;
import com.locafy.locafy_backend.model.Usuario;
import com.locafy.locafy_backend.resena.repository.ResenaRepository;
import com.locafy.locafy_backend.usuario.dto.UsuarioResponseDto;
import com.locafy.locafy_backend.usuario.repository.UsuarioRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    // Repositorios necesarios para resolver operaciones del modulo.
    private final UsuarioRepository usuarioRepository;
    private final ResenaRepository resenaRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, ResenaRepository resenaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.resenaRepository = resenaRepository;
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDto obtenerPorId(Long id) {
        // Si no existe el usuario, se responde con un 404 controlado.
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        return UsuarioResponseDto.fromEntity(usuario);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDto> listarUsuarios() {
        // Se usa DTO para no filtrar datos sensibles del usuario.
        return usuarioRepository.findAll().stream()
                .map(UsuarioResponseDto::fromEntity)
                .toList();
    }

    @Transactional
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado con id: " + id);
        }

        // Se bloquea la eliminacion si todavia existen resenas asociadas.
        if (resenaRepository.countByUsuarioId(id) > 0) {
            throw new ConflictException("No se puede eliminar el usuario porque tiene resenas asociadas");
        }

        usuarioRepository.deleteById(id);
    }
}
