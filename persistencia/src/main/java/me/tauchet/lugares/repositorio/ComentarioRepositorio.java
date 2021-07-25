package me.tauchet.lugares.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import me.tauchet.lugares.entidad.Comentario;

@Repository
public interface ComentarioRepositorio extends JpaRepository<Comentario, Integer> {
}


