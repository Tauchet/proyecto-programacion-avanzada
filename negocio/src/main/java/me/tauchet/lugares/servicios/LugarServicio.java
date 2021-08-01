package me.tauchet.lugares.servicios;

import me.tauchet.lugares.builders.ComentarioBuilder;
import me.tauchet.lugares.builders.LugarBuilder;
import me.tauchet.lugares.dto.LugarSimpleUsuarioDTO;
import me.tauchet.lugares.dto.MiLugarDTO;
import me.tauchet.lugares.excepciones.ControladaExcepcion;
import me.tauchet.lugares.excepciones.ParametrosExcepcion;
import me.tauchet.lugares.excepciones.ServicioExcepcion;
import me.tauchet.lugares.proyeccion.ComentarioBase;
import me.tauchet.lugares.proyeccion.LugarBase;
import me.tauchet.lugares.proyeccion.LugarConUsuario;
import me.tauchet.lugares.proyeccion.LugarRegistro;

import java.util.List;

public interface LugarServicio {

    int registrarLugar(LugarBuilder peticion) throws ServicioExcepcion, ParametrosExcepcion;

    List<LugarBase> buscarTodos(int categoriaId, String texto);

    List<LugarConUsuario> buscarLugaresEsperandoORechazados(int usuarioId);
    List<MiLugarDTO> buscarLugaresPorUsuario(int usuarioId);
    <T extends LugarBase> T buscarLugarPorId(int lugarId, Class<T> clase);

    void confirmarLugar(Integer lugarId, Integer usuarioId, Boolean aprobado) throws ServicioExcepcion, ParametrosExcepcion;

    void registrarFavorito(Integer usuarioId, Integer lugarId) throws ServicioExcepcion, ParametrosExcepcion;
    void eliminarFavorito(Integer usuarioId, Integer lugarId) throws ServicioExcepcion, ParametrosExcepcion;

    <T extends LugarConUsuario> LugarSimpleUsuarioDTO<T> buscarLugarSegunUsuario(int lugarId, int usuarioId, Class<T> clase) throws ServicioExcepcion, ControladaExcepcion;
    ComentarioBase registrarComentario(ComentarioBuilder builder) throws ServicioExcepcion, ParametrosExcepcion;

    void responderComentario(Integer usuarioId, Integer comentarioId, String respuesta) throws ServicioExcepcion, ParametrosExcepcion;

    List<LugarRegistro> buscarLugarRegistros(int usuarioId);
    List<LugarBase> buscarFavoritosPorUsuario(int usuarioId);

}

