package com.locafy.locafy_backend.resena.service;

import com.locafy.locafy_backend.common.exception.ResourceNotFoundException;
import com.locafy.locafy_backend.local.repository.LocalRepository;
import com.locafy.locafy_backend.model.Local;
import com.locafy.locafy_backend.model.Resena;
import com.locafy.locafy_backend.model.Usuario;
import com.locafy.locafy_backend.resena.dto.CrearResenaRequestDto;
import com.locafy.locafy_backend.resena.dto.ResenaResponseDto;
import com.locafy.locafy_backend.resena.repository.ResenaRepository;
import com.locafy.locafy_backend.usuario.repository.UsuarioRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResenaService {

    // Dependencias necesarias para validar relaciones antes de guardar.
    private final ResenaRepository resenaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LocalRepository localRepository;

    public ResenaService(
            ResenaRepository resenaRepository,
            UsuarioRepository usuarioRepository,
            LocalRepository localRepository
    ) {
        this.resenaRepository = resenaRepository;
        this.usuarioRepository = usuarioRepository;
        this.localRepository = localRepository;
    }

    @Transactional
    public ResenaResponseDto crearResena(CrearResenaRequestDto request) {
        // La resena solo se crea si usuario y local existen realmente.
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + request.getUsuarioId()));

        Local local = localRepository.findById(request.getLocalId())
                .orElseThrow(() -> new ResourceNotFoundException("Local no encontrado con id: " + request.getLocalId()));

        Resena resena = new Resena();
        resena.setComentario(request.getComentario());
        resena.setCalificacion(request.getCalificacion());
        resena.setUsuario(usuario);
        resena.setLocal(local);

        return ResenaResponseDto.fromEntity(resenaRepository.save(resena));
    }

    @Transactional(readOnly = true)
    public List<ResenaResponseDto> obtenerResenasPorLocal(Long localId) {
        // Primero se valida el local para devolver un 404 claro si no existe.
        if (!localRepository.existsById(localId)) {
            throw new ResourceNotFoundException("Local no encontrado con id: " + localId);
        }

        return resenaRepository.findByLocalId(localId).stream()
                .map(ResenaResponseDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ResenaResponseDto> obtenerResenasPorUsuario(Long usuarioId) {
        // Mismo criterio para el usuario antes de consultar sus resenas.
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuario no encontrado con id: " + usuarioId);
        }

        return resenaRepository.findByUsuarioId(usuarioId).stream()
                .map(ResenaResponseDto::fromEntity)
                .toList();
    }
}
