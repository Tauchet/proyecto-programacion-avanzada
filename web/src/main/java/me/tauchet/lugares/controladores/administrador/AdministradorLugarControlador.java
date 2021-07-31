package me.tauchet.lugares.controladores.administrador;

import me.tauchet.lugares.entidad.Rol;
import me.tauchet.lugares.excepciones.PermisosExcepcion;
import me.tauchet.lugares.excepciones.ServicioExcepcion;
import me.tauchet.lugares.proyeccion.LugarBase;
import me.tauchet.lugares.proyeccion.LugarConUsuario;
import me.tauchet.lugares.proyeccion.LugarRegistro;
import me.tauchet.lugares.servicios.LugarServicio;
import me.tauchet.lugares.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administrador/lugares")
public class AdministradorLugarControlador {

    @Autowired
    private LugarServicio lugarServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("")
    public ResponseEntity<List<LugarRegistro>> buscar(@RequestAttribute int usuarioId) throws PermisosExcepcion {
        usuarioServicio.tienePermisos(usuarioId, Rol.ADMINISTRADOR);
        List<LugarRegistro> lugarBase = lugarServicio.buscarLugarRegistros(usuarioId);
        return new ResponseEntity<>(lugarBase, HttpStatus.OK);
    }


}

