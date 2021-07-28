package me.tauchet.lugares.controladores.moderador;

import me.tauchet.lugares.proyeccion.LugarEsperando;
import me.tauchet.lugares.servicios.LugarServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/moderador/lugares")
public class ModeradorLugarControlador {

    @Autowired
    private LugarServicio lugarServicio;

    @GetMapping("/{lugarId}")
    public ResponseEntity<LugarEsperando> buscar(@PathVariable("lugarId") int lugarId) {
        LugarEsperando lugarEsperando = lugarServicio.buscarLugarPorId(lugarId);
        return new ResponseEntity<>(lugarEsperando, HttpStatus.OK);
    }

    @PutMapping("/{lugarId}/aprobar")
    public ResponseEntity<Boolean> aprobar(@PathVariable("lugarId") int lugarId) {
        System.out.println("PUT");
        lugarServicio.aprobarLugar(lugarId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping("/{lugarId}/desaprobar")
    public ResponseEntity<Boolean> desaprobar(@PathVariable("lugarId") int lugarId) {
        lugarServicio.desaprobarLugar(lugarId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<LugarEsperando>> buscarLugaresEsperando() {
        List<LugarEsperando> lugares = lugarServicio.buscarLugaresEsperando();
        return new ResponseEntity<>(lugares, HttpStatus.OK);
    }


}
