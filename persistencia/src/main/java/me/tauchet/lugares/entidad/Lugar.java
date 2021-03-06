package me.tauchet.lugares.entidad;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Lugar implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String nombre;
    private String descripcion;

    @ManyToOne
    private Categoria categoria;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Ciudad ciudad;

    @OneToMany(mappedBy="lugar")
    private List<Favorito> favoritos;

    @OneToMany(mappedBy="lugar")
    private List<Imagen> imagenes;

    @OneToMany(mappedBy="lugar")
    private List<Telefono> telefonos;

    @OneToMany(mappedBy="lugar")
    private List<Horario> horarios;

    private double latitud;
    private double longitud;

    @Enumerated(value = EnumType.STRING)
    private LugarEstado estado;

    @ManyToOne
    private Usuario moderador;

    @OneToMany(mappedBy="lugar")
    private List<Comentario> comentarios;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAprobacion;

}
