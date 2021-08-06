package me.tauchet.lugares.respuestas;

import lombok.Getter;

@Getter
public class RestParametrosRespuesta extends RestSimpleRespuesta {

    private final String field;

    public RestParametrosRespuesta(String parametro, String mensaje) {
        super(mensaje, 2500, "Excepcion de Parametros");
        this.field = parametro;
    }

}
