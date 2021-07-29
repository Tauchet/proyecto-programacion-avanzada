package me.tauchet.lugares.repositorio;

import me.tauchet.lugares.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByUsername(String username);

    Usuario findByUsernameAndPassword(String username, String contrasenia);
    Usuario findByEmailAndPassword(String email, String contrasenia);


}
