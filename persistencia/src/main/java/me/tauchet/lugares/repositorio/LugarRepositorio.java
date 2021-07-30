package me.tauchet.lugares.repositorio;

import me.tauchet.lugares.dto.LugarUsuarioDTO;
import me.tauchet.lugares.dto.MiLugarDTO;
import me.tauchet.lugares.entidad.LugarEstado;
import me.tauchet.lugares.proyeccion.LugarBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import me.tauchet.lugares.entidad.Lugar;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LugarRepositorio extends JpaRepository<Lugar, Integer> {

    @Query("SELECT lugar FROM Lugar lugar WHERE lugar.id = :lugarId")
    <T> T buscarLugarPorId(@Param("lugarId") int lugarId, Class<T> typeClass);

    @Query("SELECT lugar FROM Lugar lugar WHERE lugar.estado = :estado")
    <T> List<T> buscarLugaresPorEstado(@Param("estado") LugarEstado estado, Class<T> typeClass);

    @Query("SELECT lugar.estado FROM Lugar lugar WHERE lugar.id = :lugarId")
    LugarEstado buscarEstadoPorId(@Param("lugarId") int lugarId);

    @Query("SELECT new me.tauchet.lugares.dto.LugarUsuarioDTO(lugar, (SELECT count(favorito) FROM Favorito favorito WHERE favorito.lugar = lugar AND favorito.usuario.id = :usuarioId) > 0, (SELECT count(comentario) FROM Comentario comentario WHERE comentario.lugar = lugar AND comentario.usuario.id = :usuarioId) > 0) FROM Lugar lugar WHERE lugar.id = :lugarId")
    LugarUsuarioDTO buscarLugarPorUsuario(@Param("lugarId") int lugarId, @Param("usuarioId") int usuarioId);

    @Query("SELECT new me.tauchet.lugares.dto.MiLugarDTO(lugar.id, lugar.nombre, lugar.categoria.nombre, lugar.estado, lugar.fechaCreacion, lugar.fechaAprobacion, " +
            "(SELECT count(comentario) FROM Comentario comentario WHERE comentario.lugar = lugar AND comentario.respuesta IS NULL)) FROM Lugar lugar WHERE lugar.usuario.id = :usuarioId")
    List<MiLugarDTO> buscarLugaresPorUsuario(int usuarioId);



}


