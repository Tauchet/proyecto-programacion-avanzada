package me.tauchet.lugares.controladores;

import me.tauchet.lugares.entidad.Categoria;
import me.tauchet.lugares.entidad.Ciudad;
import me.tauchet.lugares.servicios.CategoriaServicio;
import me.tauchet.lugares.servicios.CiudadServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaControlador {

    private final CategoriaServicio categoriaServicio;

    public CategoriaControlador(CategoriaServicio categoriaServicio) {
        this.categoriaServicio = categoriaServicio;
    }

    @GetMapping("")
    public ResponseEntity<List<Categoria>> buscarTodas() {
        return new ResponseEntity<>(this.categoriaServicio.buscarTodas(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Categoria> crear(@RequestBody Categoria categoria) {
        categoria = this.categoriaServicio.crear(categoria);
        return new ResponseEntity<>(this.categoriaServicio.crear(categoria), HttpStatus.OK);
    }

}
