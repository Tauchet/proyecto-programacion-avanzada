package me.tauchet.lugares.peition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.tauchet.lugares.entidad.Rol;

@Getter
@AllArgsConstructor
public class ActualizarRolPeticion {

    private int usuarioId;
    private Rol rol;

    public ActualizarRolPeticion() {}

}
