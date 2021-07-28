package me.tauchet.lugares.respuestas;

import lombok.Data;
import me.tauchet.lugares.entidad.Categoria;
import me.tauchet.lugares.entidad.Ciudad;

import java.util.List;

@Data
public class GeneralRespuesta {

    private final List<Ciudad> ciudades;
    private final List<Categoria> categorias;

}
