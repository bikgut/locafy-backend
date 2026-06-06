package com.locafy.locafy_backend.favorito.controller;


import com.locafy.locafy_backend.favorito.service.FavoritoService;
import com.locafy.locafy_backend.model.Favorito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("favoritos")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @GetMapping
    public ResponseEntity<List<Favorito>> listarFavoritos(){
        List<Favorito> favoritos = favoritoService.obtenerFavoritos();
        if(favoritos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(favoritos);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Favorito> obtenerPorId(@PathVariable Long id){
        try{
            Favorito favorito = favoritoService.obtenerPorId(id);
            return ResponseEntity.ok(favorito);
        } catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Favorito> agregarFavorito(@RequestBody Favorito favorito){
        Favorito favoritoNuevo = favoritoService.guardarFavorito(favorito);
        return ResponseEntity.status(HttpStatus.CREATED).body(favoritoNuevo);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Favorito> actualizaFavorito(@PathVariable Long id, @RequestBody Favorito favorito){
        try{
            Favorito actualiza = favoritoService.obtenerPorId(id);
            actualiza.setId(id);
            actualiza.setLocal(favorito.getLocal());
            actualiza.setUsuario(favorito.getUsuario());
            actualiza.setFechaAgregado(favorito.getFechaAgregado());

            favoritoService.guardarFavorito(actualiza);
            return ResponseEntity.ok(actualiza);
        } catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<?> eliminarFavorito(@PathVariable Long id){
        try{
            favoritoService.borrarFavorito(id);
            return ResponseEntity.ok("Producto eliminado exitosamente!.");
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado con el id: " + id);
        }
    }
}
