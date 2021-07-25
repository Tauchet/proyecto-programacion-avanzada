package me.tauchet.lugares.controladores;

import me.tauchet.lugares.entidad.Categoria;
import me.tauchet.lugares.entidad.Ciudad;
import me.tauchet.lugares.respuestas.GeneralRespuesta;
import me.tauchet.lugares.servicios.CategoriaServicio;
import me.tauchet.lugares.servicios.CiudadServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/general")
public class GeneralControlador {

    private final CiudadServicio ciudadServicio;
    private final CategoriaServicio categoriaServicio;

    public GeneralControlador(CiudadServicio ciudadServicio, CategoriaServicio categoriaServicio) {
        this.ciudadServicio = ciudadServicio;
        this.categoriaServicio = categoriaServicio;
    }

    @GetMapping("")
    public ResponseEntity<GeneralRespuesta> buscarTodas() {
        List<Ciudad> ciudades = this.ciudadServicio.buscarTodas();
        List<Categoria> categorias = this.categoriaServicio.buscarTodas();
        GeneralRespuesta respuesta = new GeneralRespuesta(ciudades, categorias);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

}
