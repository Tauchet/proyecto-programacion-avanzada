package me.tauchet.lugares.builders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.tauchet.lugares.entidad.Horario;

import java.util.List;

@Getter
@AllArgsConstructor
public class LugarBuilder {

    private String nombre;
    private String descripcion;
    private int categoriaId;
    private int usuarioId;
    private int ciudadId;
    private double latitud;
    private double longitud;

    private List<String> imagenes;
    private List<Long> telefonos;
    private List<Horario> horarios;

    public LugarBuilder() {}

}
