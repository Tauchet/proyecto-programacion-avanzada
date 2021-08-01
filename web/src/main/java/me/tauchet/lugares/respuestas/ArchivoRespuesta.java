package me.tauchet.lugares.respuestas;

import lombok.Data;
import me.tauchet.lugares.proyeccion.CategoriaBase;
import me.tauchet.lugares.proyeccion.CiudadBase;

import java.util.List;

@Data
public class ArchivoRespuesta {

    private final String nombreArchivo;
    private final String nombreArchivoURL;
    private final String tipoArchivo;
    private final long tamanio;

}
