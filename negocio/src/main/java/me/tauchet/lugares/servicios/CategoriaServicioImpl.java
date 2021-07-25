package me.tauchet.lugares.servicios;

import me.tauchet.lugares.entidad.Categoria;
import me.tauchet.lugares.entidad.Ciudad;
import me.tauchet.lugares.repositorio.CategoriaRepositorio;
import me.tauchet.lugares.repositorio.CiudadRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServicioImpl implements CategoriaServicio {

    private final CategoriaRepositorio categoriaRepositorio;

    public CategoriaServicioImpl(CategoriaRepositorio categoriaRepositorio) {
        this.categoriaRepositorio = categoriaRepositorio;
    }

    @Override
    public List<Categoria> buscarTodas() {
        return this.categoriaRepositorio.findAll();
    }

    @Override
    public Categoria crear(Categoria categoria) {
        return this.categoriaRepositorio.save(categoria);
    }

}
