package com.locafy.locafy_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "favorito")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorito {

    @Id
    private String codigo;

    @Column(name = "id_usuario")
    private String usuario;

    @Column(name = "id_local")
    private String local;

    private LocalDateTime fechaAgregado;

}
