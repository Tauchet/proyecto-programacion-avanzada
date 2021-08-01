package me.tauchet.lugares.proyeccion;

import me.tauchet.lugares.entidad.Horario;
import me.tauchet.lugares.entidad.Imagen;
import me.tauchet.lugares.entidad.Lugar;
import me.tauchet.lugares.entidad.Telefono;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(types = { Lugar.class })
public interface LugarCompleto extends LugarConComentarios {
    
    List<TelefonoBase> getTelefonos();
    List<ImagenBase> getImagenes();
    List<HorarioBase> getHorarios();
    
    @Projection(types = { Telefono.class })
    interface TelefonoBase {

        int getId();
        long getNumero();
        
    }

    @Projection(types = { Imagen.class })
    interface ImagenBase {

        int getId();
        String getUrl();

    }

    @Projection(types = { Horario.class })
    interface HorarioBase {

        int getId();
        int getInicioHoras();
        int getInicioMinutos();

        int getFinalHoras();
        int getFinalMinutos();

        boolean lunes();
        boolean isMartes();
        boolean isMiercoles();
        boolean isJueves();
        boolean isViernes();
        boolean isSabado();
        boolean isDomingo();

    }
    
}
