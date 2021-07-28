package me.tauchet.lugares.handlers;

import me.tauchet.lugares.excepciones.ServicioExcepcion;
import me.tauchet.lugares.respuestas.RestErrorRespuesta;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExcepcionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServicioExcepcion.class)
    protected ResponseEntity<Object> handleServicioExcepcion(ServicioExcepcion ex) {
        return new ResponseEntity<>(new RestErrorRespuesta(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
