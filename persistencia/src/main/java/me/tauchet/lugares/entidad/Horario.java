package me.tauchet.lugares.entidad;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Horario implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int id;

    private int inicioHoras;
    private int inicioMinutos;

    private int finalHoras;
    private int finalMinutos;

    private boolean lunes;
    private boolean martes;
    private boolean miercoles;
    private boolean jueves;
    private boolean viernes;
    private boolean sabado;
    private boolean domingo;

    @ManyToOne
    private Lugar lugar;

}
