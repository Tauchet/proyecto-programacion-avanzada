package me.tauchet.lugares.servicios;

import me.tauchet.lugares.entidad.Ciudad;
import me.tauchet.lugares.excepciones.ServicioExcepcion;
import me.tauchet.lugares.proyeccion.CiudadBase;
import me.tauchet.lugares.repositorio.CiudadRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CiudadServicioImpl implements CiudadServicio {

    private final CiudadRepositorio ciudadRepositorio;

    public CiudadServicioImpl(CiudadRepositorio ciudadRepositorio) {
        this.ciudadRepositorio = ciudadRepositorio;
    }

    @Override
    public Ciudad crear(Ciudad ciudad) throws ServicioExcepcion {
        Optional<Ciudad> ciudadNombre = this.ciudadRepositorio.findByNombre(ciudad.getNombre());
        if (ciudadNombre.isPresent()) {
            throw new ServicioExcepcion("¡Ya hay una ciudad con este nombre!");
        }
        return ciudadRepositorio.save(ciudad);
    }

    @Override
    public List<CiudadBase> buscarTodas() {
        return this.ciudadRepositorio.buscarTodasCompletas(CiudadBase.class);
    }

    @Override
    public boolean eliminar(int ciudadId) throws ServicioExcepcion {
        Optional<Ciudad> ciudadResultado = ciudadRepositorio.findById(ciudadId);
        if (ciudadResultado.isEmpty()) {
            throw new ServicioExcepcion("¡La ciudad no se ha encontrado!");
        }
        ciudadRepositorio.deleteById(ciudadId);
        return true;

    }

}
