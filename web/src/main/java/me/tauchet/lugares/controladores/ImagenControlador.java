package me.tauchet.lugares.controladores;

import me.tauchet.lugares.excepciones.ArchivoExcepcion;
import me.tauchet.lugares.respuestas.ArchivoRespuesta;
import me.tauchet.lugares.servicios.ArchivosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/imagenes")
public class ImagenControlador {

    @Autowired
    private ArchivosServicio archivosServicio;

    @PostMapping("")
    public ArchivoRespuesta subirArchivo(@RequestParam("archivo") MultipartFile archivo) throws ArchivoExcepcion {
        String archivoFinal = archivosServicio.guardarArchivo(archivo);
        String archivoURL = ServletUriComponentsBuilder.fromCurrentContextPath().path("/images/").path(archivoFinal).toUriString();
        return new ArchivoRespuesta(archivoFinal, archivoURL, archivo.getContentType(), archivo.getSize());
    }

}
