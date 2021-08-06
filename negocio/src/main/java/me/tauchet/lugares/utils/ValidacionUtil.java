package me.tauchet.lugares.utils;

import me.tauchet.lugares.excepciones.ParametrosExcepcion;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;

public class ValidacionUtil {

    public static boolean isValidEmail(String email){
        String emailFormate ="^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\."
                + "[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        Pattern p = Pattern.compile(emailFormate);
        if(email == null){
            return false;
        }
        return p.matcher(email).matches();
    }

    public static boolean isValidURL(String url) {

        try {
            new URL(url).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }

        return true;
    }

    public static void estaVacio(String campo,String string) throws ParametrosExcepcion {
        if (string == null || string.isEmpty()) {
            throw new ParametrosExcepcion(campo, "¡Este campo se encuentra vacio!");
        }
    }

    public static void estaVacio(String campo, Object object) throws ParametrosExcepcion {
        if (object == null) {
            throw new ParametrosExcepcion(campo, "¡Este campo se encuentra vacio!");
        }
    }

    public static void esNecesarioEnLista(String campo, List<?> object, int minimo, String titulo) throws ParametrosExcepcion {
        if (object == null || object.size() < minimo) {
            throw new ParametrosExcepcion(campo, "¡Se debe ingresar al menos " + minimo + " " + titulo + "!");
        }
    }

    public static void contieneEspacios(String campo,String string) throws ParametrosExcepcion {
        if (string.contains(" ")) {
            throw new ParametrosExcepcion(campo, "¡No debe contener espacios!");
        }
    }

    public static void esCorreoValido(String campo, String string) throws ParametrosExcepcion {
        if (!isValidEmail(string)) {
            throw new ParametrosExcepcion(campo, "¡Este es un correo invalido!");
        }
    }

    public static void esURLValido(String campo, String string) throws ParametrosExcepcion {
        if (!isValidURL(string)) {
            throw new ParametrosExcepcion(campo, "¡Este es una URL invalida!");
        }
    }
}
