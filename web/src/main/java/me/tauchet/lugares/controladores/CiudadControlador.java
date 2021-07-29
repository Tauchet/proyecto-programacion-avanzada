package me.tauchet.lugares.controladores;

import me.tauchet.lugares.entidad.Ciudad;
import me.tauchet.lugares.excepciones.ServicioExcepcion;
import me.tauchet.lugares.proyeccion.CiudadBase;
import me.tauchet.lugares.servicios.CiudadServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/ciudades")
@RestController
public class CiudadControlador {

    private final CiudadServicio ciudadServicio;

    public CiudadControlador(CiudadServicio ciudadServicio) {
        this.ciudadServicio = ciudadServicio;
    }

    @GetMapping("")
    public ResponseEntity<List<CiudadBase>> buscarTodas() {
        List<CiudadBase> ciudades = this.ciudadServicio.buscarTodas();
        return new ResponseEntity<>(ciudades, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> eliminar(@PathVariable("id") int id) throws ServicioExcepcion {
        ciudadServicio.eliminar(id);
        return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
    }

    @PostMapping()
    public ResponseEntity<Ciudad> crear(@RequestBody Ciudad ciudad) throws ServicioExcepcion {
        Ciudad resultado = this.ciudadServicio.crear(ciudad);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }



}
