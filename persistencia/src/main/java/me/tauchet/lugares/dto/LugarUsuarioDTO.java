package me.tauchet.lugares.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.tauchet.lugares.entidad.Lugar;

@Getter
@AllArgsConstructor
public class LugarUsuarioDTO {

    private Lugar lugar;
    private boolean favorito;
    private boolean comentario;

}
