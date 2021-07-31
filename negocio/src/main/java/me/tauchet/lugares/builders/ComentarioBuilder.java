package me.tauchet.lugares.builders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ComentarioBuilder {

    private String texto;
    private Integer calificacion;

    @Setter
    private Integer usuarioId;

    @Setter
    private Integer lugarId;

    public ComentarioBuilder() {}

}

