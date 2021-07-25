package me.tauchet.lugares.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import me.tauchet.lugares.entidad.Telefono;

@Repository
public interface TelefonoRepositorio extends JpaRepository<Telefono, Integer> {
}


