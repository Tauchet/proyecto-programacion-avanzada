package me.tauchet.lugares;

import me.tauchet.lugares.entidad.Rol;
import me.tauchet.lugares.entidad.Usuario;
import me.tauchet.lugares.proyeccion.UsuarioBase;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Usuario.class})
public interface UsuarioConRol extends UsuarioBase {

    Rol getRol();

}
