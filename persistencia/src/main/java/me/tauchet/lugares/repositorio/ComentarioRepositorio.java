package me.tauchet.lugares.repositorio;

import me.tauchet.lugares.proyeccion.ComentarioBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import me.tauchet.lugares.entidad.Comentario;

import java.util.Optional;

@Repository
public interface ComentarioRepositorio extends JpaRepository<Comentario, Integer> {

    @Query("SELECT comentario FROM Comentario comentario WHERE comentario.lugar.id = :lugarId AND comentario.usuario.id = :usuarioId")
    Optional<ComentarioBase> buscarPorLugarYUsuario(@Param("lugarId") int lugarId, @Param("usuarioId") int usuarioId);

}


