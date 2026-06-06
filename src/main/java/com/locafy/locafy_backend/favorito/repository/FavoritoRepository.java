package com.locafy.locafy_backend.favorito.repository;

import com.locafy.locafy_backend.model.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {


}
