package com.locafy.locafy_backend.favorito.service;


import com.locafy.locafy_backend.favorito.repository.FavoritoRepository;
import com.locafy.locafy_backend.model.Favorito;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class FavoritoService {

    @Autowired
    private FavoritoRepository favoritoRepository;

    public List<Favorito> obtenerFavoritos() {return favoritoRepository.findAll();}

    public Favorito obtenerPorId(Long id) {return favoritoRepository.findById(id).orElse(null);}

    public Favorito guardarFavorito(Favorito favorito) {return favoritoRepository.save(favorito);}

    public void borrarFavorito(Long id) {favoritoRepository.deleteById(id);}
}
