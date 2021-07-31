package me.tauchet.lugares.controladores.administrador;

import me.tauchet.lugares.excepciones.ServicioExcepcion;
import me.tauchet.lugares.proyeccion.LugarBase;
import me.tauchet.lugares.proyeccion.LugarConUsuario;
import me.tauchet.lugares.proyeccion.LugarRegistro;
import me.tauchet.lugares.servicios.LugarServicio;
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

    @GetMapping("")
    public ResponseEntity<List<LugarRegistro>> buscar(@RequestAttribute int usuarioId) {
        List<LugarRegistro> lugarBase = lugarServicio.buscarLugarRegistros(usuarioId);
        return new ResponseEntity<>(lugarBase, HttpStatus.OK);
    }


}

