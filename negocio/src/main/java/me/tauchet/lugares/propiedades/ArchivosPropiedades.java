package me.tauchet.lugares.propiedades;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class ArchivosPropiedades {

    @Getter
    private String diretorioDeSubida;

}
