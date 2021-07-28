package me.tauchet.lugares.controladores;

import me.tauchet.lugares.entidad.Lugar;
import me.tauchet.lugares.builders.LugarBuilder;
import me.tauchet.lugares.excepciones.ServicioExcepcion;
import me.tauchet.lugares.proyeccion.LugarEsperando;
import me.tauchet.lugares.servicios.LugarServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lugares")
public class LugarControlador {

    private final LugarServicio lugarServicio;

    public LugarControlador(LugarServicio lugarServicio) {
        this.lugarServicio = lugarServicio;
    }


    @GetMapping("")
    public ResponseEntity<List<Lugar>> buscarTodos() {
        return new ResponseEntity<>(lugarServicio.buscarTodos(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Integer> crear(@RequestBody LugarBuilder peticion) throws ServicioExcepcion {
        int lugarId = lugarServicio.registrarLugar(peticion);
        return new ResponseEntity<>(lugarId, HttpStatus.OK);
    }

}
