package me.tauchet.lugares.controladores;

import me.tauchet.lugares.builders.ComentarioBuilder;
import me.tauchet.lugares.dto.LugarSimpleUsuarioDTO;
import me.tauchet.lugares.dto.MiLugarDTO;
import me.tauchet.lugares.entidad.Lugar;
import me.tauchet.lugares.builders.LugarBuilder;
import me.tauchet.lugares.excepciones.ControladaExcepcion;
import me.tauchet.lugares.excepciones.ParametrosExcepcion;
import me.tauchet.lugares.excepciones.ServicioExcepcion;
import me.tauchet.lugares.peition.RespuestaPeticion;
import me.tauchet.lugares.proyeccion.ComentarioBase;
import me.tauchet.lugares.proyeccion.LugarBase;
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
    public ResponseEntity<List<LugarBase>> buscarTodos(@RequestParam(value = "categoriaId", defaultValue = "-1", required = false) int categoriaId,
                                                       @RequestParam(value = "texto", defaultValue = "", required = false) String texto) {
        return new ResponseEntity<>(lugarServicio.buscarTodos(categoriaId, texto), HttpStatus.OK);
    }

    @GetMapping("/mios")
    public ResponseEntity<List<MiLugarDTO>> buscarPorUsuario(@RequestAttribute int usuarioId) {
        return new ResponseEntity<>(lugarServicio.buscarLugaresPorUsuario(usuarioId), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Integer> crear(@RequestBody LugarBuilder peticion) throws ServicioExcepcion, ParametrosExcepcion {
        peticion.setUsuarioId(1);
        int lugarId = lugarServicio.registrarLugar(peticion);
        return new ResponseEntity<>(lugarId, HttpStatus.OK);
    }

    @PostMapping("/{lugarId}/favorito")
    public ResponseEntity<Boolean> registrarFavorito(@PathVariable("lugarId") int lugarId,
                                                     @RequestAttribute int usuarioId) throws ServicioExcepcion, ParametrosExcepcion {
        lugarServicio.registrarFavorito(usuarioId, lugarId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/{lugarId}/comentarios")
    public ResponseEntity<ComentarioBase> registrarComentario(@PathVariable("lugarId") int lugarId,
                                                              @RequestBody ComentarioBuilder peticion,
                                                              @RequestAttribute int usuarioId
    ) throws ServicioExcepcion, ParametrosExcepcion {
        peticion.setLugarId(lugarId);
        peticion.setUsuarioId(usuarioId);
        return new ResponseEntity<>(lugarServicio.registrarComentario(peticion), HttpStatus.OK);
    }

    @PostMapping("/comentarios/{comentarioId}")
    public ResponseEntity<Boolean> responderComentario(@PathVariable("comentarioId") int comentarioId,
                                                       @RequestBody RespuestaPeticion peticion,
                                                       @RequestAttribute int usuarioId) throws ServicioExcepcion, ParametrosExcepcion {
        lugarServicio.responderComentario(usuarioId, comentarioId, peticion.getTexto());
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @DeleteMapping("/{lugarId}/favorito")
    public ResponseEntity<Boolean> eliminarFavorito(@PathVariable("lugarId") int lugarId,
                                                    @RequestAttribute int usuarioId) throws ServicioExcepcion, ParametrosExcepcion {
        lugarServicio.eliminarFavorito(usuarioId, lugarId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/{lugarId}")
    public ResponseEntity<LugarSimpleUsuarioDTO<LugarConComentarios>> buscarLugar(@PathVariable("lugarId") int lugarId,
                                                                                  @RequestAttribute int usuarioId) throws ServicioExcepcion, ControladaExcepcion {
        LugarSimpleUsuarioDTO<LugarConComentarios> lugar = lugarServicio.buscarLugarSegunUsuario(lugarId, usuarioId, LugarConComentarios.class);
        return new ResponseEntity<>(lugar, HttpStatus.OK);
    }

}
