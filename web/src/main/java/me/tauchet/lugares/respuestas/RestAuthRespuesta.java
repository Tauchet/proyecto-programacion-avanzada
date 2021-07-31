package me.tauchet.lugares.respuestas;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RestErrorRespuesta {

    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;

    public RestErrorRespuesta(String mensaje) {
        this.timestamp = LocalDateTime.now();
        this.status = 1000;
        this.error = "Service Error";
        this.message = mensaje;
    }

}
