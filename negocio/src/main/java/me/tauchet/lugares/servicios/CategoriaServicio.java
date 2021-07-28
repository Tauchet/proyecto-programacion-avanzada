package me.tauchet.lugares.servicios;

import me.tauchet.lugares.entidad.Categoria;
import me.tauchet.lugares.excepciones.ServicioExcepcion;

import java.util.List;

public interface CategoriaServicio {

    List<Categoria> buscarTodas();
    Categoria crear(Categoria categoria);

    boolean eliminar(int ciudadId) throws ServicioExcepcion;

}
