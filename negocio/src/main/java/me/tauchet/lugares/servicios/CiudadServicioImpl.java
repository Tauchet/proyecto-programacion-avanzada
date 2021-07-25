package me.tauchet.lugares.servicios;

import me.tauchet.lugares.entidad.Ciudad;
import me.tauchet.lugares.repositorio.CiudadRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CiudadServicioImpl implements CiudadServicio {

    private final CiudadRepositorio ciudadRepositorio;

    public CiudadServicioImpl(CiudadRepositorio ciudadRepositorio) {
        this.ciudadRepositorio = ciudadRepositorio;
    }

    @Override
    public Ciudad crear(Ciudad ciudad) {
        return ciudadRepositorio.save(ciudad);
    }

    @Override
    public List<Ciudad> buscarTodas() {
        return this.ciudadRepositorio.findAll();
    }

}
