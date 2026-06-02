package com.locafy.locafy_backend.usuario.repository;

import com.locafy.locafy_backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Se aprovecha los metodos base de JpaRepository para este modulo.
}
