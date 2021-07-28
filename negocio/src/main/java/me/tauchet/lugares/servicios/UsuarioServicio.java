package me.tauchet.lugares.servicios;

import me.tauchet.lugares.builders.UsuarioBuilder;
import me.tauchet.lugares.entidad.Usuario;

import java.security.NoSuchAlgorithmException;

public interface UsuarioServicio {

    void registrarUsuario(UsuarioBuilder builder) throws Exception;

    Usuario buscarUsuario(String nombre, String contrasenia) throws NoSuchAlgorithmException, Exception;
    Usuario buscarUsuarioPorNombreOCorreo(String nombreOCorre, String contrasenia) throws NoSuchAlgorithmException, Exception;

}
