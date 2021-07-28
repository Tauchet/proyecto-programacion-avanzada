package me.tauchet.lugares.proyeccion;

import me.tauchet.lugares.entidad.Lugar;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = Lugar.class)
public interface LugarEsperando {

    int getId();
    String getNombre();
    String getDescripcion();
    Usuario getUsuario();

    @Projection(types = Usuario.class)
    interface Usuario {

        String getUsername();
        String getNombre();
        String getAvatarUrl();

    }


}
