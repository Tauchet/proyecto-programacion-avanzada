package me.tauchet.lugares.controladores;

import me.tauchet.lugares.builders.ComentarioBuilder;
import me.tauchet.lugares.dto.LugarSimpleUsuarioDTO;
import me.tauchet.lugares.entidad.Lugar;
import me.tauchet.lugares.builders.LugarBuilder;
import me.tauchet.lugares.excepciones.ServicioExcepcion;
import me.tauchet.lugares.peition.RespuestaPeticion;
import me.tauchet.lugares.proyeccion.ComentarioBase;
import me.tauchet.lugares.proyeccion.LugarConComentarios;
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

    @PostMapping("/{lugarId}/favorito")
    public ResponseEntity<Boolean> registrarFavorito(@PathVariable("lugarId") int lugarId) throws ServicioExcepcion {
        int usuarioId = 44;
        lugarServicio.registrarFavorito(usuarioId, lugarId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/{lugarId}/comentarios")
    public ResponseEntity<ComentarioBase> registrarComentario(@PathVariable("lugarId") int lugarId, @RequestBody ComentarioBuilder peticion) throws ServicioExcepcion {
        peticion.setLugarId(lugarId);
        peticion.setUsuarioId(44);
        return new ResponseEntity<>(lugarServicio.registrarComentario(peticion), HttpStatus.OK);
    }

    @PostMapping("/comentarios/{comentarioId}")
    public ResponseEntity<Boolean> responderComentario(@PathVariable("comentarioId") int comentarioId,
                                                              @RequestBody RespuestaPeticion peticion) throws ServicioExcepcion {
        int usuarioId = 44;
        lugarServicio.responderComentario(usuarioId, comentarioId, peticion.getTexto());
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @DeleteMapping("/{lugarId}/favorito")
    public ResponseEntity<Boolean> eliminarFavorito(@PathVariable("lugarId") int lugarId) throws ServicioExcepcion {
        int usuarioId = 44;
        lugarServicio.eliminarFavorito(usuarioId, lugarId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/{lugarId}")
    public ResponseEntity<LugarSimpleUsuarioDTO<LugarConComentarios>> buscarLugar(@PathVariable("lugarId") int lugarId) throws ServicioExcepcion {
        int usuarioId = 44;
        LugarSimpleUsuarioDTO<LugarConComentarios> lugar = lugarServicio.buscarLugarPorUsuario(lugarId, usuarioId, LugarConComentarios.class);
        return new ResponseEntity<>(lugar, HttpStatus.OK);
    }

}
