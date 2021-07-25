package me.tauchet.lugares.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import me.tauchet.lugares.entidad.Categoria;

@Repository
public interface CategoriaRepositorio extends JpaRepository<Categoria, Integer> {
}

