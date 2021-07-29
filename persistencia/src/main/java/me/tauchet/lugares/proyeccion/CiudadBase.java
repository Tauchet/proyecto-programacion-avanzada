package me.tauchet.lugares.proyeccion;

import me.tauchet.lugares.entidad.Categoria;
import me.tauchet.lugares.entidad.Ciudad;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Ciudad.class})
public interface CiudadBase {

    int getId();
    String getNombre();

}
