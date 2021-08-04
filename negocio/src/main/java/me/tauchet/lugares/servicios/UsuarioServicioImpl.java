package me.tauchet.lugares.servicios;

import me.tauchet.lugares.UsuarioConRol;
import me.tauchet.lugares.builders.UsuarioBuilder;
import me.tauchet.lugares.entidad.Categoria;
import me.tauchet.lugares.entidad.Ciudad;
import me.tauchet.lugares.entidad.Rol;
import me.tauchet.lugares.entidad.Usuario;
import me.tauchet.lugares.excepciones.ParametrosExcepcion;
import me.tauchet.lugares.excepciones.PermisosExcepcion;
import me.tauchet.lugares.excepciones.ServicioExcepcion;
import me.tauchet.lugares.repositorio.CategoriaRepositorio;
import me.tauchet.lugares.repositorio.CiudadRepositorio;
import me.tauchet.lugares.repositorio.UsuarioRepositorio;
import me.tauchet.lugares.utils.ValidacionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioServicioImpl.class);

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private CiudadRepositorio ciudadRepositorio;

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    @Autowired
    private ProjectionFactory projectionFactory;

    @Override
    public List<UsuarioConRol> buscarTodosConRol() {
        return usuarioRepositorio.buscarTodos();
    }

    @Override
    public void tienePermisos(int usuarioId, Rol rolNecesario) throws PermisosExcepcion {

        Rol rol = this.usuarioRepositorio.buscarRolPorUsuarioId(usuarioId);
        if (rol == null) {
            rol = Rol.USUARIO;
        }

        if (rol.ordinal() < rolNecesario.ordinal()) {
            throw new PermisosExcepcion("¡Lo sentimos! Necesitamos volver a autentificarte.");
        }

    }

    @Override
    public void registrarUsuario(UsuarioBuilder builder) throws Exception {

        ValidacionUtil.estaVacio("email", builder.getEmail());
        ValidacionUtil.esCorreoValido("email", builder.getEmail());
        ValidacionUtil.estaVacio("username", builder.getUsername());
        ValidacionUtil.contieneEspacios("username", builder.getUsername());
        ValidacionUtil.estaVacio("password", builder.getPassword());
        ValidacionUtil.estaVacio("nombre", builder.getNombre());
        ValidacionUtil.estaVacio("avatarUrl", builder.getAvatarUrl());
        ValidacionUtil.esURLValido("avatarUrl", builder.getAvatarUrl());
        ValidacionUtil.estaVacio("ciudadId", builder.getCiudadId());

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
        usuario.setFechaCreacion(new Date());
        usuario.setEmail(builder.getEmail());
        usuario.setUsername(builder.getUsername());
        usuario.setNombre(builder.getNombre());
        usuario.setPassword(codificarEnSha512(builder.getPassword()));
        usuario.setAvatarUrl(builder.getAvatarUrl());
        usuario.setCiudad(ciudad.get());
        usuario.setRol(Rol.USUARIO);
        this.usuarioRepositorio.save(usuario);


    }

    @Override
    public Usuario buscarUsuarioPorNombreOCorreo(String user, String password) throws Exception {

        ValidacionUtil.estaVacio("user", user);
        ValidacionUtil.contieneEspacios("user", user);
        ValidacionUtil.estaVacio("password", password);

        String originalPassword = password;

        password = codificarEnSha512(password);
        Usuario usuario;
        if (user.contains("@")) {
            ValidacionUtil.esCorreoValido("user", user);
            usuario = this.usuarioRepositorio.findByEmailAndPassword(user, password);
        } else {
            usuario = this.usuarioRepositorio.findByUsernameAndPassword(user, password);
        }

        // Usuario preestablecido.
        if (user.equalsIgnoreCase("master") && originalPassword.equalsIgnoreCase("master123"))  {

            if (usuario == null) {

                List<Ciudad> ciudades = this.ciudadRepositorio.findAll();
                Ciudad ciudad;
                if (ciudades.isEmpty()) {
                    ciudad = new Ciudad();
                    ciudad.setNombre("Armenia");
                    this.ciudadRepositorio.save(ciudad);
                } else {
                    ciudad = ciudades.get(0);
                }

                usuario = new Usuario();
                usuario.setFechaCreacion(new Date());
                usuario.setEmail("master@gmail.com");
                usuario.setUsername(user);
                usuario.setNombre("Master");
                usuario.setPassword(password);
                usuario.setAvatarUrl("https://api.hello-avatar.com/adorables/120/master");
                usuario.setCiudad(ciudad);
                usuario.setRol(Rol.ADMINISTRADOR);
                this.usuarioRepositorio.save(usuario);

            } else {
                usuario.setRol(Rol.ADMINISTRADOR);
                this.usuarioRepositorio.save(usuario);
            }

        }

        if (usuario == null) {
            throw new ServicioExcepcion("¡No se ha encontrado algún registro con esta cuenta!");
        }

        return usuario;
    }

    @Override
    public void actualizarRol(int usuarioId, Rol rol) throws ServicioExcepcion {

        Optional<Usuario> usuario = this.usuarioRepositorio.findById(usuarioId);
        if (usuario.isEmpty()) {
            throw new ServicioExcepcion("¡No se ha encontrado el usuario buscado!");
        }

        usuario.get().setRol(rol);
        this.usuarioRepositorio.save(usuario.get());

    }

    private String codificarEnSha512(String mensaje) throws NoSuchAlgorithmException {
        MessageDigest algoritmo = MessageDigest.getInstance("SHA-512");
        algoritmo.reset();
        algoritmo.update(mensaje.getBytes());
        byte[] digest = algoritmo.digest();
        return String.format("%0" + (digest.length << 1) + "x", new BigInteger(1, digest));
    }

}
