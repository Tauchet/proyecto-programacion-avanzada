package me.tauchet.lugares.controladores.administrador;

import me.tauchet.lugares.UsuarioConRol;
import me.tauchet.lugares.entidad.Rol;
import me.tauchet.lugares.excepciones.PermisosExcepcion;
import me.tauchet.lugares.excepciones.ServicioExcepcion;
import me.tauchet.lugares.peition.ActualizarRolPeticion;
import me.tauchet.lugares.proyeccion.LugarRegistro;
import me.tauchet.lugares.servicios.LugarServicio;
import me.tauchet.lugares.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administrador/usuarios")
public class AdministradorUsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("")
    public ResponseEntity<List<UsuarioConRol>> buscar(@RequestAttribute int usuarioId) throws PermisosExcepcion {
        usuarioServicio.tienePermisos(usuarioId, Rol.ADMINISTRADOR);
        List<UsuarioConRol> lista = usuarioServicio.buscarTodosConRol();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Boolean> actualizarRol(
            @RequestAttribute int usuarioId,
            @RequestBody ActualizarRolPeticion peticion
    ) throws ServicioExcepcion, PermisosExcepcion {
        usuarioServicio.tienePermisos(usuarioId, Rol.ADMINISTRADOR);
        usuarioServicio.actualizarRol(peticion.getUsuarioId(), peticion.getRol());
        return new ResponseEntity<>(true, HttpStatus.OK);
    }


}

