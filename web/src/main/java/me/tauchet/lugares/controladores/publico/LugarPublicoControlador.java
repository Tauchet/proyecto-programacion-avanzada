package me.tauchet.lugares.controladores.publico;

import me.tauchet.lugares.builders.ComentarioBuilder;
import me.tauchet.lugares.builders.LugarBuilder;
import me.tauchet.lugares.builders.UsuarioBuilder;
import me.tauchet.lugares.entidad.Lugar;
import me.tauchet.lugares.entidad.Usuario;
import me.tauchet.lugares.excepciones.ParametrosExcepcion;
import me.tauchet.lugares.excepciones.ServicioExcepcion;
import me.tauchet.lugares.proyeccion.LugarBase;
import me.tauchet.lugares.proyeccion.LugarConFavoritos;
import me.tauchet.lugares.respuestas.RestObjetoRespuesta;
import me.tauchet.lugares.respuestas.RestSimpleRespuesta;
import me.tauchet.lugares.servicios.LugarServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publico/lugares")
public class LugarPublicoControlador {

    private final LugarServicio lugarServicio;

    public LugarPublicoControlador(LugarServicio lugarServicio) {
        this.lugarServicio = lugarServicio;
    }

    @GetMapping("")
    public ResponseEntity<List<LugarConFavoritos>> buscarLugaresTodos() {
        return new ResponseEntity<>(lugarServicio.buscarTodos(LugarConFavoritos.class), HttpStatus.OK);
    }

    @GetMapping("/nombre")
    public ResponseEntity<LugarConFavoritos> buscarLugarPorNombre(@RequestParam(name = "texto") String texto) throws ServicioExcepcion {
        return new ResponseEntity<>(lugarServicio.buscarLugarPorNombre(texto, LugarConFavoritos.class), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LugarConFavoritos> buscarLugar(@PathVariable(name = "id") Integer lugarId) throws ServicioExcepcion {
        return new ResponseEntity<>(lugarServicio.buscarLugarPorId(lugarId, LugarConFavoritos.class), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<RestObjetoRespuesta> crearLugar(@RequestBody LugarBuilder builder) throws ServicioExcepcion, ParametrosExcepcion {
        LugarBase resultado = lugarServicio.registrarLugar(builder);
        return new ResponseEntity<>(new RestObjetoRespuesta(201, resultado, "¡Se ha creado correctamente!"), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestSimpleRespuesta> actualizarLugar(@PathVariable(name = "id") Integer lugarId, @RequestBody Lugar lugar) throws ServicioExcepcion, ParametrosExcepcion {
        lugarServicio.actualizarLugar(lugarId, lugar);
        return new ResponseEntity<>(new RestSimpleRespuesta(200, "¡Se ha actualizado correctamente!"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestSimpleRespuesta> eliminarLugar(@PathVariable(name = "id") Integer lugarId) throws ServicioExcepcion {
        lugarServicio.eliminarLugarPorId(lugarId);
        return new ResponseEntity<>(new RestSimpleRespuesta(200, "¡Se ha eliminado correctamente!"), HttpStatus.OK);
    }

    @PostMapping("favorito/{lugarId}/{usuarioId}")
    public ResponseEntity<RestSimpleRespuesta> registrarFavorito(@PathVariable(name = "lugarId") Integer lugarId, @PathVariable(name = "usuarioId") Integer usuarioId) throws ServicioExcepcion, ParametrosExcepcion {
        lugarServicio.registrarFavorito(usuarioId, lugarId);
        return new ResponseEntity<>(new RestSimpleRespuesta(200, "¡Se ha registrado el favorito correctamente!"), HttpStatus.OK);
    }

    @PostMapping("comentario/{lugarId}/{usuarioId}")
    public ResponseEntity<RestSimpleRespuesta> registrarComentario(
            @PathVariable(name = "lugarId") Integer lugarId,
            @PathVariable(name = "usuarioId") Integer usuarioId,
            @RequestBody ComentarioBuilder builder
            ) throws ServicioExcepcion, ParametrosExcepcion {
        builder.setLugarId(lugarId);
        builder.setUsuarioId(usuarioId);
        lugarServicio.registrarComentario(builder);
        return new ResponseEntity<>(new RestSimpleRespuesta(200, "¡Se ha registrado el comentario correctamente!"), HttpStatus.OK);
    }


}
