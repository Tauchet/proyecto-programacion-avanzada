package me.tauchet.lugares.servicios;

import me.tauchet.lugares.builders.UsuarioBuilder;
import me.tauchet.lugares.entidad.Ciudad;
import me.tauchet.lugares.entidad.Rol;
import me.tauchet.lugares.entidad.Usuario;
import me.tauchet.lugares.excepciones.ServicioExcepcion;
import me.tauchet.lugares.repositorio.CiudadRepositorio;
import me.tauchet.lugares.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private CiudadRepositorio ciudadRepositorio;

    @Autowired
    private ProjectionFactory projectionFactory;

    @Override
    public void registrarUsuario(UsuarioBuilder builder) throws Exception {

        Optional<Usuario> usuarioCorreo = usuarioRepositorio.findByEmail(builder.getEmail());
        if (usuarioCorreo.isPresent()) {
            throw new ServicioExcepcion("¡Ya existe un usuario con este correo!");
        }

        Optional<Usuario> usuarioName = usuarioRepositorio.findByUsername(builder.getUsername());
        if (usuarioName.isPresent()) {
            throw new ServicioExcepcion("¡Ya existe un usuario con este nombre de usuario!");
        }

        Optional<Ciudad> ciudad = ciudadRepositorio.findById(builder.getCiudadId());
        if (ciudad.isEmpty()) {
            throw new ServicioExcepcion("¡No se ha encontrado la ciudad solicitada!");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(builder.getEmail());
        usuario.setUsername(builder.getUsername());
        usuario.setNombre(builder.getNombre());
        usuario.setPassword(codificarEnSha512(builder.getPassword()));
        usuario.setAvatarUrl(builder.getAvatarUrl());
        usuario.setCiudad(ciudad.get());
        usuario.setRol(Rol.USUARIO);
        usuario = this.usuarioRepositorio.save(usuario);

        //projectionFactory.createProjection(UsuarioDTO.class, usuario);

    }

    @Override
    public Usuario buscarUsuario(String nombre, String contrasenia) throws Exception {

        contrasenia = codificarEnSha512(contrasenia);

        Usuario usuario = this.usuarioRepositorio.findByUsernameAndPassword(nombre, contrasenia);
        if (usuario == null) {
            throw new ServicioExcepcion("¡No se ha encontrado algún usuario con estos datos!");
        }

        return usuario;
    }

    @Override
    public Usuario buscarUsuarioPorNombreOCorreo(String nombreOCorre, String contrasenia) throws Exception {

        contrasenia = codificarEnSha512(contrasenia);

        Usuario usuario;
        if (nombreOCorre.contains("@")) {
            usuario = this.usuarioRepositorio.findByEmailAndPassword(nombreOCorre, contrasenia);
        } else {
            usuario = this.usuarioRepositorio.findByUsernameAndPassword(nombreOCorre, contrasenia);
        }

        if (usuario == null) {
            throw new ServicioExcepcion("¡No se ha encontrado algún usuario con estos datos!");
        }

        return usuario;
    }

    private String codificarEnSha512(String mensaje) throws NoSuchAlgorithmException {
        MessageDigest algoritmo = MessageDigest.getInstance("SHA-512");
        algoritmo.reset();
        algoritmo.update(mensaje.getBytes());
        byte[] digest = algoritmo.digest();
        return String.format("%0" + (digest.length << 1) + "x", new BigInteger(1, digest));
    }

}
