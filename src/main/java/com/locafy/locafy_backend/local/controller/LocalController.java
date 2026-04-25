package com.locafy.locafy_backend.local.controller;

import com.locafy.locafy_backend.local.service.LocalService;
import com.locafy.locafy_backend.model.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("local")
public class LocalController {

    @Autowired
    private LocalService localService;

    @GetMapping
    public ResponseEntity<List<Local>> listarLocales(){
        List<Local> local = localService.obtenerLocales();
        if(local.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(local);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Local> obtenerPorId(@PathVariable String id){
        try{
            Local local = localService.obtenerPorId(id);
            return ResponseEntity.ok(local);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Local> agregarLocal(@RequestBody Local local){
        Local localNuevo = localService.guardarLocal(local);
        return ResponseEntity.status(HttpStatus.CREATED).body(localNuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Local> actualizarLocal(@PathVariable String id, @RequestBody Local local){
        try{
            Local actualiza = localService.obtenerPorId(id);
            actualiza.setId(id);
            actualiza.setNombre(local.getNombre());
            actualiza.setDireccion(local.getDireccion());
            actualiza.setComuna(local.getComuna());
            actualiza.setCiudad(local.getCiudad());
            actualiza.setCategorias(local.getCategorias());
            actualiza.setMedioPago(local.getMedioPago());
            actualiza.setHorarioLocal(local.getHorarioLocal());
            actualiza.setTelefonoContacto(local.getTelefonoContacto());
            actualiza.setImagenUrl(local.getImagenUrl());
            actualiza.setActivo(local.getActivo());
            actualiza.setLatitud(local.getLatitud());
            actualiza.setLongitud(local.getLongitud());

            localService.guardarLocal(actualiza);
            return ResponseEntity.ok(actualiza);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarLocal(@PathVariable String id){
        try{
            localService.borrarLocal(id);
            return ResponseEntity.ok("Producto eliminado exitosamente!.");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado con el id: " + id);

        }
    }

}
