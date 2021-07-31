package me.tauchet.lugares.controladores.administrador;

import me.tauchet.lugares.entidad.Categoria;
import me.tauchet.lugares.entidad.Rol;
import me.tauchet.lugares.excepciones.PermisosExcepcion;
import me.tauchet.lugares.excepciones.ServicioExcepcion;
import me.tauchet.lugares.proyeccion.CategoriaBase;
import me.tauchet.lugares.servicios.CategoriaServicio;
import me.tauchet.lugares.servicios.UsuarioServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/administrador/categorias")
@RestController
public class AdministradorCategoriaControlador {

    private final CategoriaServicio categoriaServicio;
    private final UsuarioServicio usuarioServicio;

    public AdministradorCategoriaControlador(CategoriaServicio categoriaServicio, UsuarioServicio usuarioServicio) {
        this.categoriaServicio = categoriaServicio;
        this.usuarioServicio = usuarioServicio;
    }

    @GetMapping("")
    public ResponseEntity<List<CategoriaBase>> buscarTodas(@RequestAttribute int usuarioId) throws PermisosExcepcion {
        usuarioServicio.tienePermisos(usuarioId, Rol.ADMINISTRADOR);
        return new ResponseEntity<>(this.categoriaServicio.buscarTodas(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Categoria> crear(@RequestBody Categoria categoria, @RequestAttribute int usuarioId) throws PermisosExcepcion {
        usuarioServicio.tienePermisos(usuarioId, Rol.ADMINISTRADOR);
        Categoria resultado = this.categoriaServicio.crear(categoria);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }


}
