package me.tauchet.lugares.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import me.tauchet.lugares.entidad.Lugar;

@Repository
public interface LugarRepositorio extends JpaRepository<Lugar, Integer> {
}


