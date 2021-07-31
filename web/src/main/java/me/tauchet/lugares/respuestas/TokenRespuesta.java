package me.tauchet.lugares.respuestas;

import lombok.Data;
import me.tauchet.lugares.entidad.Rol;

@Data
public class TokenRespuesta {

    private final String token;
    private final Rol rol;
    private final int usuarioId;

}
