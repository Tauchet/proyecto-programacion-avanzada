package me.tauchet.lugares.proyeccion;

import me.tauchet.lugares.entidad.Lugar;
import me.tauchet.lugares.entidad.LugarEstado;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = { Lugar.class })
public interface LugarBase {

    int getId();
    String getNombre();
    String getDescripcion();
    LugarEstado getEstado();
    Date getFechaCreacion();
    double getLongitud();
    double getLatitud();

    CategoriaBase getCategoria();
    CiudadBase getCiudad();

}
