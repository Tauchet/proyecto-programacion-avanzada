package me.tauchet.lugares.servicios;

import me.tauchet.lugares.builders.ComentarioBuilder;
import me.tauchet.lugares.builders.LugarBuilder;
import me.tauchet.lugares.dto.LugarSimpleUsuarioDTO;
import me.tauchet.lugares.dto.MiLugarDTO;
import me.tauchet.lugares.entidad.Lugar;
import me.tauchet.lugares.excepciones.ControladaExcepcion;
import me.tauchet.lugares.excepciones.ParametrosExcepcion;
import me.tauchet.lugares.excepciones.ServicioExcepcion;
import me.tauchet.lugares.proyeccion.*;

import java.util.List;

public interface LugarServicio {

    LugarBase registrarLugar(LugarBuilder peticion) throws ServicioExcepcion, ParametrosExcepcion;

    List<LugarBase> buscarTodos(int categoriaId, String texto);
    <T extends LugarBase> List<T> buscarTodos(Class<T> clase);

    List<LugarConUsuario> buscarLugaresEsperandoORechazados(int usuarioId);
    List<MiLugarDTO> buscarLugaresPorUsuario(int usuarioId);

    <T extends LugarBase> T buscarLugarPorNombre(String nombre, Class<T> clase) throws ServicioExcepcion;
    <T extends LugarBase> T buscarLugarPorId(int lugarId, Class<T> clase) throws ServicioExcepcion;

    void confirmarLugar(Integer lugarId, Integer usuarioId, Boolean aprobado) throws ServicioExcepcion, ParametrosExcepcion;

    void registrarFavorito(Integer usuarioId, Integer lugarId) throws ServicioExcepcion, ParametrosExcepcion;
    void eliminarFavorito(Integer usuarioId, Integer lugarId) throws ServicioExcepcion, ParametrosExcepcion;

    <T extends LugarConUsuario> LugarSimpleUsuarioDTO<T> buscarLugarSegunUsuario(int lugarId, int usuarioId, Class<T> clase) throws ServicioExcepcion, ControladaExcepcion;
    ComentarioBase registrarComentario(ComentarioBuilder builder) throws ServicioExcepcion, ParametrosExcepcion;

    void responderComentario(Integer usuarioId, Integer comentarioId, String respuesta) throws ServicioExcepcion, ParametrosExcepcion;

    List<LugarRegistro> buscarLugarRegistros(int usuarioId);
    List<LugarBase> buscarFavoritosPorUsuario(int usuarioId);

    void eliminarLugarPorId(int lugarId) throws ServicioExcepcion;

    void actualizarLugar(Integer lugarId, Lugar lugar) throws ParametrosExcepcion, ServicioExcepcion;


}

