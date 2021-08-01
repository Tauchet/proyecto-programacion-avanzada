package me.tauchet.lugares.servicios;

import me.tauchet.lugares.excepciones.ArchivoExcepcion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class ArchivosServicioImpl implements ArchivosServicio {

    private static final Logger logger = LoggerFactory.getLogger(ArchivosServicioImpl.class);
    private final Path ubicacionDeArchivo;

    public ArchivosServicioImpl() {
        this.ubicacionDeArchivo = Paths.get("../images").toAbsolutePath().normalize();
        logger.info("Ubicación de Archivos: " + this.ubicacionDeArchivo);
        try {
            Files.createDirectories(this.ubicacionDeArchivo);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String guardarArchivo(MultipartFile archivo) throws ArchivoExcepcion {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(archivo.getOriginalFilename()));
        String finalFileName = UUID.randomUUID().toString();
        try {

            if (fileName.contains("..")) {
                throw new ArchivoExcepcion("El archivo contiene un nombre invalido.");
            }

            // Copiar el archivo.
            Path targetLocation = this.ubicacionDeArchivo.resolve(finalFileName);
            Files.copy(archivo.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return finalFileName;
        } catch (Throwable ex) {
            throw new ArchivoExcepcion("¡Ha ocurrido un error! El archivo no se ha podido subir");
        }
    }

}
