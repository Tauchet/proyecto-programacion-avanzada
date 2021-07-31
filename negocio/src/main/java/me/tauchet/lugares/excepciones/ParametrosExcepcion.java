package me.tauchet.lugares.excepciones;

import lombok.Getter;

@Getter
public class ParametrosExcepcion extends Exception {

    private final String campo;

    public ParametrosExcepcion(String campo, String mensaje) {
        super(mensaje);
        this.campo = campo;
    }

}

