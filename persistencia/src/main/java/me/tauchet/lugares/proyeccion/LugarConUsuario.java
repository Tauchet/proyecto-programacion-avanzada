package me.tauchet.lugares.proyeccion;

import me.tauchet.lugares.entidad.Lugar;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = { Lugar.class })
public interface LugarConUsuario extends LugarBase {

    UsuarioBase getUsuario();


}
