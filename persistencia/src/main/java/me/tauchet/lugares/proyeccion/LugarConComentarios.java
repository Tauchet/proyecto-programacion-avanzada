package me.tauchet.lugares.proyeccion;

import me.tauchet.lugares.entidad.Lugar;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(types = { Lugar.class })
public interface LugarConComentarios extends LugarBase {

    List<ComentarioBase> getComentarios();

}
