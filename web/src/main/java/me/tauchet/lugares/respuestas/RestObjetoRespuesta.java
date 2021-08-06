package me.tauchet.lugares.respuestas;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RestObjetoRespuesta {

    private final LocalDateTime timestamp;
    private final int status;
    private final Object data;
    private final String message;

    public RestObjetoRespuesta(int status, Object data, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.data = data;
        this.message = message;
    }


}
