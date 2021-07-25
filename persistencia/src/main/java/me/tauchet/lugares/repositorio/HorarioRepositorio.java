package me.tauchet.lugares.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import me.tauchet.lugares.entidad.Horario;

@Repository
public interface HorarioRepositorio extends JpaRepository<Horario, Integer> {
}


