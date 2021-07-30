package me.tauchet.lugares.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.tauchet.lugares.entidad.LugarEstado;

import java.util.Date;

@Getter
@AllArgsConstructor
public class MiLugarDTO {

    private int id;
    private String nombre;
    private String categoria;
    private LugarEstado estado;
    private Date fechaCreacion;
    private Date fechaAprobacion;
    private long comentariosSinRespuesta;

}
