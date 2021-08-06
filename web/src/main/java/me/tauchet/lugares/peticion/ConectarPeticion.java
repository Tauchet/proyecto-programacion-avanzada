package me.tauchet.lugares.peticion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConectarPeticion {

    private String user;
    private String password;

    public ConectarPeticion() {}

}
