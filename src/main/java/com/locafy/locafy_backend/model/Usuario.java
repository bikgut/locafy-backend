package com.locafy.locafy_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "usuarios")
public class Usuario {

    // Identificador principal del usuario dentro del sistema.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre visible que se mostrara en la app.
    @Column(nullable = false)
    private String nombre;

    // Se mantiene unico para evitar cuentas duplicadas.
    @Column(nullable = false, unique = true)
    private String email;

    // La clave se guarda persistida para el proceso de autenticacion.
    @Column(nullable = false)
    private String password;

    // Campo simple para distinguir permisos o tipo de usuario.
    @Enumerated(EnumType.STRING)
    private Rol rol;

}
