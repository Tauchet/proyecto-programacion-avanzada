package me.tauchet.lugares;

import me.tauchet.lugares.propiedades.ArchivosPropiedades;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ArchivosPropiedades.class })
public class NegocioApplication {

    public static void main(String[] args) {
        SpringApplication.run(NegocioApplication.class, args);
    }

}
