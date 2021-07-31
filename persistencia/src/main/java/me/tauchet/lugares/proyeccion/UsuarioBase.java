package me.tauchet.lugares.proyeccion;

import me.tauchet.lugares.entidad.Usuario;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = { Usuario.class })
public interface UsuarioBase {

    int getId();
    String getUsername();
    String getNombre();
    String getAvatarUrl();

}
