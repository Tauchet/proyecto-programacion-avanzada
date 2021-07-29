package me.tauchet.lugares.repositorio;

import me.tauchet.lugares.proyeccion.CategoriaBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import me.tauchet.lugares.entidad.Categoria;

import java.util.List;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Integer> {

    @Query("SELECT categoria FROM Categoria categoria")
    List<CategoriaBase> buscarTodas();

}

