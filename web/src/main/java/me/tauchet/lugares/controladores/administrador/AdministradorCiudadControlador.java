package me.tauchet.lugares.controladores.administrador;

import me.tauchet.lugares.entidad.Ciudad;
import me.tauchet.lugares.entidad.Rol;
import me.tauchet.lugares.excepciones.PermisosExcepcion;
import me.tauchet.lugares.excepciones.ServicioExcepcion;
import me.tauchet.lugares.proyeccion.CiudadBase;
import me.tauchet.lugares.servicios.CategoriaServicio;
import me.tauchet.lugares.servicios.CiudadServicio;
import me.tauchet.lugares.servicios.UsuarioServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/administrador/ciudades")
@RestController
public class AdministradorCiudadControlador {

    private final CiudadServicio ciudadServicio;
    private final UsuarioServicio usuarioServicio;

    public AdministradorCiudadControlador(CiudadServicio ciudadServicio, UsuarioServicio usuarioServicio) {
        this.ciudadServicio = ciudadServicio;
        this.usuarioServicio = usuarioServicio;
    }

    @GetMapping("")
    public ResponseEntity<List<CiudadBase>> buscarTodas(@RequestAttribute int usuarioId) throws PermisosExcepcion {
        usuarioServicio.tienePermisos(usuarioId, Rol.ADMINISTRADOR);
        List<CiudadBase> ciudades = this.ciudadServicio.buscarTodas();
        return new ResponseEntity<>(ciudades, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Ciudad> crear(@RequestBody Ciudad ciudad, @RequestAttribute int usuarioId) throws PermisosExcepcion, ServicioExcepcion {
        usuarioServicio.tienePermisos(usuarioId, Rol.ADMINISTRADOR);
        Ciudad resultado = this.ciudadServicio.crear(ciudad);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }



}
