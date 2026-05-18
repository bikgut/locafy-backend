package com.locafy.locafy_backend.resena.repository;

import com.locafy.locafy_backend.model.Resena;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {

    // Se usa join fetch para devolver la resena con usuario y local cargados.
    @Query("""
            select r from Resena r
            join fetch r.usuario
            join fetch r.local
            where r.local.id = :localId
            """)
    List<Resena> findByLocalId(Long localId);

    // Mismo criterio para evitar problemas al mapear la respuesta.
    @Query("""
            select r from Resena r
            join fetch r.usuario
            join fetch r.local
            where r.usuario.id = :usuarioId
            """)
    List<Resena> findByUsuarioId(Long usuarioId);

    // Sirve para validar si un usuario tiene resenas antes de eliminarlo.
    long countByUsuarioId(Long usuarioId);
}
