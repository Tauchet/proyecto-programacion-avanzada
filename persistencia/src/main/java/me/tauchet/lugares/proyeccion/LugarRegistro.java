package me.tauchet.lugares.proyeccion;

import me.tauchet.lugares.entidad.Lugar;
import me.tauchet.lugares.entidad.LugarEstado;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {Lugar.class})
public interface LugarRegistro {

    int getId();
    String getNombre();
    LugarEstado getEstado();

    CategoriaBase getCategoria();
    CiudadBase getCiudad();

    Date getFechaAprobacion();

    UsuarioBase getUsuario();
    UsuarioBase getModerador();

}
