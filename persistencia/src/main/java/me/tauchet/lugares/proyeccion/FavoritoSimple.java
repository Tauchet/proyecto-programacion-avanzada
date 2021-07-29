package me.tauchet.lugares.proyeccion;

import me.tauchet.lugares.entidad.Favorito;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = Favorito.class)
public interface FavoritoSimple {

    Lugar getLugar();
    Usuario getUsuario();

    interface Lugar {

        int getId();

    }

    interface Usuario {

        int getId();

    }

}
