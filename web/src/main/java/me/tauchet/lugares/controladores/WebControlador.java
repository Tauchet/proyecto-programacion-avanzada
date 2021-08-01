package me.tauchet.lugares.controladores;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.springframework.web.servlet.HandlerMapping.*;

@RestController
@RequestMapping("*")
public class WebControlador {

    private final Path frontendPath;
    private final Path imagesPath;
    private final Resource defaultIndex;

    public WebControlador() throws FileNotFoundException {
        this.frontendPath = Paths.get("../frontend/dist").toAbsolutePath().normalize();
        this.imagesPath = Paths.get("../images").toAbsolutePath().normalize();
        this.defaultIndex = loadResource(this.frontendPath, "index.html", true);
    }

    @GetMapping("**")
    public ResponseEntity<Object> renderizar() throws FileNotFoundException {

        RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
        String path = Objects.requireNonNull(attributes.getAttribute(LOOKUP_PATH, 0)).toString();

        // Es una imagén.
        if (path.startsWith("/images")) {
            return new ResponseEntity<>(loadResource(this.imagesPath, path.replace("/images/", ""), false), HttpStatus.OK);
        }

        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        Resource resource = null;
        if (path.isEmpty()) {
            resource = defaultIndex;
        } else {
            resource = loadResource(this.frontendPath, path, true);
        }
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }


    private Resource loadResource(Path directory, String fileName, boolean redirect) throws FileNotFoundException {
        try {
            Path filePath = directory.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                if (redirect) {
                    return this.defaultIndex;
                } else {
                    throw new FileNotFoundException("¡Archivo sin encontrar!");
                }
            }
        } catch (Exception ex) {
           if (redirect)  {
               return this.defaultIndex;
           } else {
               throw new FileNotFoundException("¡Archivo sin encontrar!");
           }
        }
    }

}
