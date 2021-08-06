package me.tauchet.lugares.handlers;

import me.tauchet.lugares.respuestas.RestSimpleRespuesta;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestPriorityExcepcionHandler {

    @ExceptionHandler(SizeLimitExceededException.class)
    @ResponseStatus(value = HttpStatus.PAYLOAD_TOO_LARGE)
    protected ResponseEntity<Object> handleSizeLimitExceededException(SizeLimitExceededException ex) {
        return new ResponseEntity<>(new RestSimpleRespuesta(
                "¡El tamaño de la imagén es muy grande!",
                3000,
                "Excepcion de Archivo"
        ), HttpStatus.PAYLOAD_TOO_LARGE);
    }

}
