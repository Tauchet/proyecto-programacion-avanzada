package me.tauchet.lugares.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import me.tauchet.lugares.entidad.Favorito;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface FavoritoRepositorio extends JpaRepository<Favorito, Integer> {

    @Query("SELECT favorito FROM Favorito favorito WHERE favorito.lugar.id = :lugarId AND favorito.usuario.id = :usuarioId")
    <T> Optional<T> buscarFavorito(@Param("usuarioId") int usuarioId, @Param("lugarId") int lugarId, Class<T> typeClass);

    @Transactional
    @Modifying
    @Query("DELETE FROM Favorito favorito WHERE favorito.lugar.id = :lugarId AND favorito.usuario.id = :usuarioId")
    void eliminarFavorito(int usuarioId, int lugarId);

}


