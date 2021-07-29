package me.tauchet.lugares.builders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ComentarioBuilder {

    private String texto;
    private int calificacion;
    private String descripcion;

    @Setter
    private int usuarioId;

    @Setter
    private int lugarId;

    public ComentarioBuilder() {}

}

