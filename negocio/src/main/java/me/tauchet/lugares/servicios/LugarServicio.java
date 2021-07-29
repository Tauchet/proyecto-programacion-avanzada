package me.tauchet.lugares.servicios;

import me.tauchet.lugares.builders.ComentarioBuilder;
import me.tauchet.lugares.builders.LugarBuilder;
import me.tauchet.lugares.dto.LugarSimpleUsuarioDTO;
import me.tauchet.lugares.entidad.Lugar;
import me.tauchet.lugares.excepciones.ServicioExcepcion;
import me.tauchet.lugares.proyeccion.ComentarioBase;
import me.tauchet.lugares.proyeccion.LugarBase;

import java.util.List;

public interface LugarServicio {

    int registrarLugar(LugarBuilder peticion) throws ServicioExcepcion;

    List<Lugar> buscarTodos();
    List<LugarBase> buscarLugaresEsperando();
    <T extends LugarBase> T buscarLugarPorId(int lugarId, Class<T> clase);

    void confirmarLugar(int lugarId, int usuarioId, boolean aprobado) throws ServicioExcepcion;

    void registrarFavorito(int usuarioId, int lugarId) throws ServicioExcepcion;
    void eliminarFavorito(int usuarioId, int lugarId) throws ServicioExcepcion;

    <T extends LugarBase> LugarSimpleUsuarioDTO<T> buscarLugarPorUsuario(int lugarId, int usuarioId, Class<T> clase) throws ServicioExcepcion;
    ComentarioBase registrarComentario(ComentarioBuilder builder) throws ServicioExcepcion;

    void responderComentario(int usuarioId, int comentarioId, String respuesta) throws ServicioExcepcion;

}

