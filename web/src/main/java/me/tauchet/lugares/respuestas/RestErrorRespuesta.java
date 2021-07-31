package me.tauchet.lugares.respuestas;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RestErrorRespuesta {

    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;

    public RestErrorRespuesta(String mensaje, int estado, String errorName) {
        this.timestamp = LocalDateTime.now();
        this.status = estado;
        this.error = errorName;
        this.message = mensaje;
    }

}
