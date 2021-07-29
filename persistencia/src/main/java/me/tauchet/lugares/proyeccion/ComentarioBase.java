package me.tauchet.lugares.proyeccion;

import me.tauchet.lugares.entidad.Comentario;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = { Comentario.class })
public interface ComentarioBase {

    int getId();
    String getTexto();
    int getCalificacion();
    String getRespuesta();
    Date getFechaComentario();

    UsuarioBase getUsuario();

}
