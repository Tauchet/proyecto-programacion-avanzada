package me.tauchet.lugares.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.tauchet.lugares.proyeccion.LugarBase;

@Getter
@AllArgsConstructor
public class LugarSimpleUsuarioDTO<T extends LugarBase> {

    private T lugar;
    private boolean favorito;

}
