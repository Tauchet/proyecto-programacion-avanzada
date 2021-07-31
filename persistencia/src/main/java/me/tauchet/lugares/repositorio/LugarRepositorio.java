package me.tauchet.lugares.repositorio;

import me.tauchet.lugares.dto.LugarUsuarioDTO;
import me.tauchet.lugares.dto.MiLugarDTO;
import me.tauchet.lugares.entidad.LugarEstado;
import me.tauchet.lugares.proyeccion.LugarBase;
import me.tauchet.lugares.proyeccion.LugarRegistro;
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

    @Query("SELECT lugar FROM Lugar lugar WHERE lugar.estado = 'ESPERANDO' OR (lugar.estado = 'DESAPROBADO' AND lugar.moderador IS NOT NULL AND lugar.moderador.id = :usuarioId)")
    <T> List<T> buscarLugaresEsperandoORechazados(@Param("usuarioId") int usuarioId, Class<T> typeClass);

    @Query("SELECT lugar.estado FROM Lugar lugar WHERE lugar.id = :lugarId")
    LugarEstado buscarEstadoPorId(@Param("lugarId") int lugarId);

    @Query("SELECT new me.tauchet.lugares.dto.LugarUsuarioDTO(lugar, (SELECT count(favorito) FROM Favorito favorito WHERE favorito.lugar = lugar AND favorito.usuario.id = :usuarioId) > 0, (SELECT count(comentario) FROM Comentario comentario WHERE comentario.lugar = lugar AND comentario.usuario.id = :usuarioId) > 0) FROM Lugar lugar WHERE lugar.id = :lugarId")
    LugarUsuarioDTO buscarLugarSegunUsuario(@Param("lugarId") int lugarId, @Param("usuarioId") int usuarioId);

    @Query("SELECT new me.tauchet.lugares.dto.MiLugarDTO(lugar.id, lugar.nombre, lugar.categoria.nombre, lugar.estado, lugar.fechaCreacion, lugar.fechaAprobacion, " +
            "(SELECT count(comentario) FROM Comentario comentario WHERE comentario.lugar = lugar AND comentario.respuesta IS NULL)) FROM Lugar lugar WHERE lugar.usuario.id = :usuarioId")
    List<MiLugarDTO> buscarLugaresPorUsuario(int usuarioId);

    @Query("SELECT lugar FROM Lugar lugar WHERE lugar.moderador IS NOT NULL ORDER BY lugar.fechaAprobacion DESC")
    List<LugarRegistro> buscarLugarRegistros();

    @Query("SELECT lugar FROM Lugar lugar")
    <T> List<T> buscarTodos(Class<T> typeClass);

    @Query("SELECT lugar FROM Lugar lugar WHERE lugar.categoria.id = :categoriaId AND lugar.nombre LIKE %:texto%")
    <T> List<T> buscarTodosPorCategoriaYNombre(@Param("categoriaId") int categoriaId, @Param("texto") String texto, Class<T> typeClass);

    @Query("SELECT lugar FROM Lugar lugar WHERE lugar.categoria.id = :categoriaId")
    <T> List<T> buscarTodosPorCategoria(@Param("categoriaId") int categoriaId, Class<T> typeClass);

    @Query("SELECT lugar FROM Lugar lugar WHERE lugar.nombre LIKE %:texto%")
    <T> List<T> buscarTodosPorNombre(@Param("texto") String texto, Class<T> typeClass);


}


