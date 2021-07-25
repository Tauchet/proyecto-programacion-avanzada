package me.tauchet.lugares.servicios;

import me.tauchet.lugares.entidad.Ciudad;

import java.util.List;

public interface CiudadServicio {

    List<Ciudad> buscarTodas();
    Ciudad crear(Ciudad ciudad);

}
