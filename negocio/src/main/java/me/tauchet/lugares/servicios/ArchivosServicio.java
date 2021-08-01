package me.tauchet.lugares.servicios;

import me.tauchet.lugares.excepciones.ArchivoExcepcion;
import org.springframework.web.multipart.MultipartFile;

public interface ArchivosServicio {

    String guardarArchivo(MultipartFile file) throws ArchivoExcepcion;

}
