package me.tauchet.lugares.controladores.moderador;

import me.tauchet.lugares.excepciones.ServicioExcepcion;
import me.tauchet.lugares.proyeccion.LugarBase;
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
    public ResponseEntity<LugarBase> buscar(@PathVariable("lugarId") int lugarId) {
        LugarBase lugarBase = lugarServicio.buscarLugarPorId(lugarId, LugarBase.class);
        return new ResponseEntity<>(lugarBase, HttpStatus.OK);
    }

    @PutMapping("/{lugarId}/aprobar")
    public ResponseEntity<Boolean> aprobar(@PathVariable("lugarId") int lugarId) throws ServicioExcepcion {
        int usuarioId = 44;
        lugarServicio.confirmarLugar(lugarId, usuarioId, true);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping("/{lugarId}/desaprobar")
    public ResponseEntity<Boolean> desaprobar(@PathVariable("lugarId") int lugarId) throws ServicioExcepcion {
        int usuarioId = 44;
        lugarServicio.confirmarLugar(lugarId, usuarioId, false);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<LugarBase>> buscarLugaresEsperando() {
        List<LugarBase> lugares = lugarServicio.buscarLugaresEsperando();
        return new ResponseEntity<>(lugares, HttpStatus.OK);
    }


}
