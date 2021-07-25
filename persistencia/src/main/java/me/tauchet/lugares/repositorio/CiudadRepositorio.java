package me.tauchet.lugares.repositorio;

import me.tauchet.lugares.entidad.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CiudadRepositorio extends JpaRepository<Ciudad, Integer> {
}

