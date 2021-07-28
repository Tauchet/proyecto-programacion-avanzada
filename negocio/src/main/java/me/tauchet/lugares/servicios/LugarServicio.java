package me.tauchet.lugares.servicios;

import me.tauchet.lugares.builders.LugarBuilder;
import me.tauchet.lugares.entidad.Lugar;
import me.tauchet.lugares.excepciones.ServicioExcepcion;
import me.tauchet.lugares.proyeccion.LugarEsperando;

import java.util.List;

public interface LugarServicio {

    int registrarLugar(LugarBuilder peticion) throws ServicioExcepcion;

    List<Lugar> buscarTodos();
    List<LugarEsperando> buscarLugaresEsperando();
    LugarEsperando buscarLugarPorId(int lugarId);

    void aprobarLugar(int lugarId);
    void desaprobarLugar(int lugarId);

}

