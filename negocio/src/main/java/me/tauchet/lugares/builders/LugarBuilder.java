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
    private Integer categoriaId;
    private Integer usuarioId;
    private Integer ciudadId;
    private Double latitud;
    private Double longitud;

    private List<String> imagenes;
    private List<Long> telefonos;
    private List<Horario> horarios;

    public LugarBuilder() {}

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

}
