package me.tauchet.lugares.controladores;

import me.tauchet.lugares.builders.UsuarioBuilder;
import me.tauchet.lugares.peition.ConectarPeticion;
import me.tauchet.lugares.servicios.UsuarioServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @PostMapping("/registro")
    public ResponseEntity<Boolean> registrarUsuario(@RequestBody UsuarioBuilder builder) throws Exception {
        usuarioServicio.registrarUsuario(builder);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/conectar")
    public ResponseEntity<Boolean> conectarUsuario(@RequestBody ConectarPeticion peticion) throws Exception {
        usuarioServicio.buscarUsuarioPorNombreOCorreo(peticion.getUser(), peticion.getPassword());
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
