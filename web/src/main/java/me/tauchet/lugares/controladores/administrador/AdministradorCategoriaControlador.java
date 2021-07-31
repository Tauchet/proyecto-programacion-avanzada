package me.tauchet.lugares.controladores;

import me.tauchet.lugares.entidad.Categoria;
import me.tauchet.lugares.excepciones.ServicioExcepcion;
import me.tauchet.lugares.proyeccion.CategoriaBase;
import me.tauchet.lugares.servicios.CategoriaServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/categorias")
@RestController
public class CategoriaControlador {

    private final CategoriaServicio categoriaServicio;

    public CategoriaControlador(CategoriaServicio categoriaServicio) {
        this.categoriaServicio = categoriaServicio;
    }

    @GetMapping("")
    public ResponseEntity<List<CategoriaBase>> buscarTodas() {
        return new ResponseEntity<>(this.categoriaServicio.buscarTodas(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Categoria> crear(@RequestBody Categoria categoria) {
        Categoria resultado = this.categoriaServicio.crear(categoria);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }
    

}
