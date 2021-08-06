package me.tauchet.lugares.respuestas;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RestSimpleRespuesta {

    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;

    public RestSimpleRespuesta(int status, String mensaje) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = null;
        this.message = mensaje;
    }


    public RestSimpleRespuesta(String mensaje, int estado, String errorName) {
        this.timestamp = LocalDateTime.now();
        this.status = estado;
        this.error = errorName;
        this.message = mensaje;
    }

}
