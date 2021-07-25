package me.tauchet.lugares.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import me.tauchet.lugares.entidad.Imagen;

@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, Integer> {
}


