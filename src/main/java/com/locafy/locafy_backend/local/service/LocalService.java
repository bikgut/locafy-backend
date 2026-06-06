package com.locafy.locafy_backend.local.service;

import com.locafy.locafy_backend.local.repository.LocalRepository;
import com.locafy.locafy_backend.model.Local;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class LocalService {

    @Autowired
    private LocalRepository localRepository;

    public List<Local> obtenerLocales() {return localRepository.findAll();}

    public Local obtenerPorId(Long id) {return localRepository.findById(id).orElse(null);}

    public Local guardarLocal(Local local) {return localRepository.save(local);}

    public void borrarLocal(Long id) {localRepository.deleteById(id);}
}
