package com.locafy.locafy_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name= "local")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String comuna;

    @Column(nullable = false)
    private String ciudad;

    @Column(nullable = false)
    private String medioPago;

    @Column(nullable = false)
    private String horarioLocal;

    @Column(nullable = false)
    private String telefonoContacto;

    @Column(nullable = false)
    private String imagenUrl;

    @Column(nullable = false)
    private Boolean activo;

    @Column(nullable = false)
    private Double ratingPromedio;

    @Column(nullable = false)
    private Integer cantidadResenas;

    @Column(nullable = false)
    private double latitud;

    @Column(nullable = false)
    private double longitud;

    @Column(nullable = false)
    private String placeIdGoogle;

    @ElementCollection(targetClass = CategoriaLocal.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "local_categorias", joinColumns = @JoinColumn(name = "local_id"))
    @Column(name = "categoria")
    private Set<CategoriaLocal> categorias = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "dueno_id")
    private User dueno;


}
