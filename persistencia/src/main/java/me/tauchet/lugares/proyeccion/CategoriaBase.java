package me.tauchet.lugares.proyeccion;

import me.tauchet.lugares.entidad.Categoria;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Categoria.class})
public interface CategoriaBase {

    int getId();
    String getNombre();

}
