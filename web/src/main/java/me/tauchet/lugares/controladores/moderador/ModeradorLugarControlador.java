package me.tauchet.lugares.controladores.moderador;

import me.tauchet.lugares.entidad.Rol;
import me.tauchet.lugares.excepciones.ParametrosExcepcion;
import me.tauchet.lugares.excepciones.PermisosExcepcion;
import me.tauchet.lugares.excepciones.ServicioExcepcion;
import me.tauchet.lugares.proyeccion.LugarBase;
import me.tauchet.lugares.proyeccion.LugarConUsuario;
import me.tauchet.lugares.servicios.LugarServicio;
import me.tauchet.lugares.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/moderador/lugares")
public class ModeradorLugarControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private LugarServicio lugarServicio;

    @GetMapping("/{lugarId}")
    public ResponseEntity<LugarBase> buscar(@PathVariable("lugarId") int lugarId, @RequestAttribute int usuarioId) throws PermisosExcepcion {
        usuarioServicio.tienePermisos(usuarioId, Rol.MODERADOR);
        LugarBase lugarBase = lugarServicio.buscarLugarPorId(lugarId, LugarBase.class);
        return new ResponseEntity<>(lugarBase, HttpStatus.OK);
    }

    @PutMapping("/{lugarId}/aprobar")
    public ResponseEntity<Boolean> aprobar(@PathVariable("lugarId") int lugarId,
                                           @RequestAttribute int usuarioId) throws ServicioExcepcion, ParametrosExcepcion, PermisosExcepcion {
        usuarioServicio.tienePermisos(usuarioId, Rol.MODERADOR);
        lugarServicio.confirmarLugar(lugarId, usuarioId, true);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping("/{lugarId}/desaprobar")
    public ResponseEntity<Boolean> desaprobar(@PathVariable("lugarId") int lugarId,
                                              @RequestAttribute int usuarioId) throws ServicioExcepcion, ParametrosExcepcion, PermisosExcepcion {

        usuarioServicio.tienePermisos(usuarioId, Rol.MODERADOR);
        lugarServicio.confirmarLugar(lugarId, usuarioId, false);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<LugarConUsuario>> informarLugares(@RequestAttribute int usuarioId) throws PermisosExcepcion {
        usuarioServicio.tienePermisos(usuarioId, Rol.MODERADOR);
        List<LugarConUsuario> lugares = lugarServicio.buscarLugaresEsperandoORechazados(usuarioId);
        return new ResponseEntity<>(lugares, HttpStatus.OK);
    }


}
