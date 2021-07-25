package me.tauchet.lugares.controladores;

import me.tauchet.lugares.entidad.Ciudad;
import me.tauchet.lugares.servicios.CiudadServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ciudades")
public class CiudadControlador {

    private final CiudadServicio ciudadServicio;

    public CiudadControlador(CiudadServicio ciudadServicio) {
        this.ciudadServicio = ciudadServicio;
    }

    @GetMapping("")
    public ResponseEntity<List<Ciudad>> buscarTodas() {
        return new ResponseEntity<>(this.ciudadServicio.buscarTodas(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Ciudad> crear(@RequestBody Ciudad ciudad) {
        ciudad = this.ciudadServicio.crear(ciudad);
        return new ResponseEntity<>(this.ciudadServicio.crear(ciudad), HttpStatus.OK);
    }

}
