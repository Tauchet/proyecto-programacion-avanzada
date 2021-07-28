package me.tauchet.lugares.servicios;

import me.tauchet.lugares.entidad.Ciudad;
import me.tauchet.lugares.excepciones.ServicioExcepcion;

import java.util.List;

public interface CiudadServicio {

    List<Ciudad> buscarTodas();
    Ciudad crear(Ciudad ciudad) throws ServicioExcepcion;

    boolean eliminar(int ciudadId) throws ServicioExcepcion;

}
