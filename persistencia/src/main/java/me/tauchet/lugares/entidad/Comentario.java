package me.tauchet.lugares.entidad;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Comentario {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int id;

    private String texto;
    private int calificacion;
    private String respuesta;

    @Temporal(TemporalType.DATE)
    private Date fechaComentario;


    // NOTAS: Agregar fecha de la respuesta

    @ManyToOne
    private Lugar lugar;


    @ManyToOne
    private Usuario usuario;

}
