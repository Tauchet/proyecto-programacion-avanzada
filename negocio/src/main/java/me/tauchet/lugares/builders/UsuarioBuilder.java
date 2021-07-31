package me.tauchet.lugares.builders;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsuarioBuilder {

    private String email;
    private String username;
    private String password;
    private String nombre;
    private String avatarUrl;
    private Integer ciudadId;

    public UsuarioBuilder() {}

}