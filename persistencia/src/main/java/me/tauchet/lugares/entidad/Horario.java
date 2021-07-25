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

    private String horario;

    @OneToOne
    private Lugar lugar;

}
