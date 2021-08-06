package me.tauchet.lugares.respuestas;

public class RestAuthRespuesta extends RestSimpleRespuesta {

    public RestAuthRespuesta(String mensaje) {
        super(mensaje, 1500, "Excepcion de Autentificación");
    }

}
