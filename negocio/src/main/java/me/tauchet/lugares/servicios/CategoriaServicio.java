package me.tauchet.lugares.servicios;

import me.tauchet.lugares.entidad.Categoria;
import me.tauchet.lugares.entidad.Ciudad;

import java.util.List;

public interface CategoriaServicio {

    List<Categoria> buscarTodas();
    Categoria crear(Categoria categoria);

}
