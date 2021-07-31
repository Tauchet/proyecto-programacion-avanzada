package me.tauchet.lugares.respuestas;

import lombok.Getter;
import me.tauchet.lugares.handlers.RestExcepcionHandler;

import java.time.LocalDateTime;

public class RestAuthRespuesta extends RestErrorRespuesta {

    public RestAuthRespuesta(String mensaje) {
        super(mensaje, 1500, "Excepcion de Autentificación");
    }

}
