package me.tauchet.lugares.servicios;

import me.tauchet.lugares.UsuarioConRol;
import me.tauchet.lugares.builders.UsuarioBuilder;
import me.tauchet.lugares.entidad.Rol;
import me.tauchet.lugares.entidad.Usuario;
import me.tauchet.lugares.excepciones.PermisosExcepcion;
import me.tauchet.lugares.excepciones.ServicioExcepcion;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UsuarioServicio {

    void registrarUsuario(UsuarioBuilder builder) throws Exception;

    Usuario buscarUsuarioPorNombreOCorreo(String nombreOCorre, String contrasenia) throws NoSuchAlgorithmException, Exception;

    List<UsuarioConRol> buscarTodosConRol();

    void actualizarRol(int usuarioId, Rol rol) throws ServicioExcepcion;

    void tienePermisos(int usuarioId, Rol rolNecesario) throws PermisosExcepcion;

}
