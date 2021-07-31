package me.tauchet.lugares.controladores;

import me.tauchet.lugares.builders.UsuarioBuilder;
import me.tauchet.lugares.entidad.Usuario;
import me.tauchet.lugares.peition.ConectarPeticion;
import me.tauchet.lugares.respuestas.TokenRespuesta;
import me.tauchet.lugares.servicios.UsuarioServicio;
import me.tauchet.lugares.util.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UsuarioControlador {

    private final JwtTokenUtil jwtTokenUtil;
    private final UsuarioServicio usuarioServicio;

    public UsuarioControlador(JwtTokenUtil jwtTokenUtil, UsuarioServicio usuarioServicio) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.usuarioServicio = usuarioServicio;
    }

    @PostMapping("/registro")
    public ResponseEntity<Boolean> registrarUsuario(@RequestBody UsuarioBuilder builder) throws Exception {
        usuarioServicio.registrarUsuario(builder);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/conectar")
    public ResponseEntity<TokenRespuesta> conectarUsuario(@RequestBody ConectarPeticion peticion) throws Exception {
        Usuario usuario = usuarioServicio.buscarUsuarioPorNombreOCorreo(peticion.getUser(), peticion.getPassword());
        String token = this.jwtTokenUtil.generarToken(usuario.getId(), usuario.getRol());
        TokenRespuesta tokenRespuesta = new TokenRespuesta(token, usuario.getRol(), usuario.getId());
        return new ResponseEntity<>(tokenRespuesta, HttpStatus.OK);
    }

}
