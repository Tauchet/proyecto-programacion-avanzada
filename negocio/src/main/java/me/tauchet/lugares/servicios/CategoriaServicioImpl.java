package me.tauchet.lugares.servicios;

import me.tauchet.lugares.entidad.Categoria;
import me.tauchet.lugares.entidad.Ciudad;
import me.tauchet.lugares.excepciones.ServicioExcepcion;
import me.tauchet.lugares.repositorio.CategoriaRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public boolean eliminar(int categoriaId) throws ServicioExcepcion {
        Optional<Categoria> categoriaResultado = this.categoriaRepositorio.findById(categoriaId);
        if (categoriaResultado.isEmpty()) {
            throw new ServicioExcepcion("¡La categoria no se ha encontrado!");
        }
        this.categoriaRepositorio.deleteById(categoriaId);
        return true;

    }

}
