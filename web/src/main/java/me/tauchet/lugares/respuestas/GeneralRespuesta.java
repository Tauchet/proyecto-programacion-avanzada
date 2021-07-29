package me.tauchet.lugares.respuestas;

import lombok.Data;
import me.tauchet.lugares.entidad.Categoria;
import me.tauchet.lugares.entidad.Ciudad;
import me.tauchet.lugares.proyeccion.CategoriaBase;
import me.tauchet.lugares.proyeccion.CiudadBase;

import java.util.List;

@Data
public class GeneralRespuesta {

    private final List<CiudadBase> ciudades;
    private final List<CategoriaBase> categorias;

}
