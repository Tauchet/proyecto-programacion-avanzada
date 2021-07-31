package me.tauchet.lugares.repositorio;

import me.tauchet.lugares.UsuarioConRol;
import me.tauchet.lugares.entidad.Rol;
import me.tauchet.lugares.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByUsername(String username);

    Usuario findByUsernameAndPassword(String username, String contrasenia);
    Usuario findByEmailAndPassword(String email, String contrasenia);

    @Query("SELECT usuario.rol FROM Usuario usuario WHERE usuario.id = :usuarioId")
    Rol buscarRolPorUsuarioId(@Param("usuarioId") int usuarioId);

    @Query("SELECT usuario FROM Usuario usuario")
    List<UsuarioConRol> buscarTodos();


}
