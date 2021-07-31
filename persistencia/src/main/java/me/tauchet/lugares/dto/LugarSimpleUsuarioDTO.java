package me.tauchet.lugares.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.tauchet.lugares.proyeccion.LugarBase;
import me.tauchet.lugares.proyeccion.LugarConUsuario;

@Getter
@AllArgsConstructor
public class LugarSimpleUsuarioDTO<T extends LugarConUsuario> {

    private T lugar;
    private boolean favorito;
    private boolean comentario;

}
