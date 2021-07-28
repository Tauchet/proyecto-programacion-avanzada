package me.tauchet.lugares.repositorio;

import me.tauchet.lugares.entidad.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CiudadRepositorio extends JpaRepository<Ciudad, Integer> {

    Optional<Ciudad> findByNombre(String nombre);

    @Query("SELECT c FROM Ciudad c")
    List<Ciudad> buscarTodas();

    @Query("SELECT c FROM Ciudad c")
    List<Ciudad> buscarTodasCompletas();

}