package me.tauchet.lugares.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import me.tauchet.lugares.entidad.Categoria;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Integer> {


}

