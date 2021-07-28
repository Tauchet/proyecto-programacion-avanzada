package me.tauchet.lugares.servicios;

import lombok.RequiredArgsConstructor;
import me.tauchet.lugares.builders.LugarBuilder;
import me.tauchet.lugares.entidad.*;
import me.tauchet.lugares.excepciones.ServicioExcepcion;
import me.tauchet.lugares.proyeccion.LugarEsperando;
import me.tauchet.lugares.repositorio.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LugarServicioImpl implements LugarServicio {

    private final LugarRepositorio lugarRepositorio;
    private final CiudadRepositorio ciudadRepositorio;
    private final CategoriaRepositorio categoriaRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final HorarioRepositorio horarioRepositorio;
    private final TelefonoRepositorio telefonoRepositorio;

    @Override
    public int registrarLugar(LugarBuilder peticion) throws ServicioExcepcion {

        Optional<Ciudad> ciudad = this.ciudadRepositorio.findById(peticion.getCiudadId());
        if (ciudad.isEmpty()) {
            throw new ServicioExcepcion("¡No se ha encontrado la ciudad seleccionada!");
        }

        Optional<Categoria> categoria = this.categoriaRepositorio.findById(peticion.getCategoriaId());
        if (categoria.isEmpty()) {
            throw new ServicioExcepcion("¡No se ha encontrado la categoria seleccionada!");
        }

        Optional<Usuario> usuario = this.usuarioRepositorio.findById(peticion.getUsuarioId());
        if (usuario.isEmpty()) {
            throw new ServicioExcepcion("¡No se ha encontrado el usuario registrado!");
        }

        Lugar lugar = new Lugar();
        lugar.setFechaCreacion(new Date());
        lugar.setNombre(peticion.getNombre());
        lugar.setDescripcion(peticion.getDescripcion());
        lugar.setCategoria(categoria.get());
        lugar.setUsuario(usuario.get());
        lugar.setCiudad(ciudad.get());
        lugar.setLongitud(peticion.getLongitud());
        lugar.setLatitud(peticion.getLatitud());
        lugar.setEstado(LugarEstado.ESPERANDO);
        lugarRepositorio.save(lugar);

        // Guardar horarios
        for (Horario horario: peticion.getHorarios()) {
            horario.setLugar(lugar);
            horarioRepositorio.save(horario);
        }

        // Guardar telefonos
        for (long numero: peticion.getTelefonos()) {
            Telefono telefono = new Telefono();
            telefono.setNumero(numero);
            telefono.setLugar(lugar);
            telefonoRepositorio.save(telefono);
        }

        return lugar.getId();
    }

    @Override
    public List<Lugar> buscarTodos() {
        return lugarRepositorio.findAll();
    }

    @Override
    public List<LugarEsperando> buscarLugaresEsperando() {
        return lugarRepositorio.buscarLugaresPorEstado(LugarEstado.ESPERANDO, LugarEsperando.class);
    }

    @Override
    public LugarEsperando buscarLugarPorId(int lugarId) {
        return lugarRepositorio.buscarLugarPorId(lugarId, LugarEsperando.class);
    }

    @Override
    public void aprobarLugar(int lugarId) {
        lugarRepositorio.actualizarEstado(lugarId, LugarEstado.APROBADO);
    }

    @Override
    public void desaprobarLugar(int lugarId) {
        lugarRepositorio.actualizarEstado(lugarId, LugarEstado.DESAPROBADO);
    }

}
