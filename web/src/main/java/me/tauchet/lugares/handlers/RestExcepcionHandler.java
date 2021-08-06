package me.tauchet.lugares.handlers;

import me.tauchet.lugares.excepciones.*;
import me.tauchet.lugares.respuestas.RestAuthRespuesta;
import me.tauchet.lugares.respuestas.RestSimpleRespuesta;
import me.tauchet.lugares.respuestas.RestParametrosRespuesta;
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
        return new ResponseEntity<>(new RestSimpleRespuesta(
                ex.getMessage(),
                2000,
                "Excepcion de Servicio"
        ), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ControladaExcepcion.class)
    protected ResponseEntity<Object> handleServicioExcepcion(ControladaExcepcion ex) {
        return new ResponseEntity<>(new RestSimpleRespuesta(
                ex.getMessage(),
                1000,
                "Excepcion Controlada"
        ), HttpStatus.GONE);
    }

    @ExceptionHandler(ArchivoExcepcion.class)
    protected ResponseEntity<Object> handleServicioExcepcion(ArchivoExcepcion ex) {
        return new ResponseEntity<>(new RestSimpleRespuesta(
                ex.getMessage(),
                3000,
                "Excepcion de Archivo"
        ), HttpStatus.GONE);
    }


    @ExceptionHandler(ParametrosExcepcion.class)
    protected ResponseEntity<Object> handleServicioExcepcion(ParametrosExcepcion ex) {
        return new ResponseEntity<>(new RestParametrosRespuesta(
                ex.getCampo(),
                ex.getMessage()
        ), HttpStatus.GONE);
    }


    @ExceptionHandler(PermisosExcepcion.class)
    protected ResponseEntity<Object> handleServicioExcepcion(PermisosExcepcion ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(new RestAuthRespuesta(ex.getMessage()), HttpStatus.GONE);
    }

}
