package me.tauchet.lugares.servicios;

import lombok.RequiredArgsConstructor;
import me.tauchet.lugares.builders.ComentarioBuilder;
import me.tauchet.lugares.builders.LugarBuilder;
import me.tauchet.lugares.dto.LugarSimpleUsuarioDTO;
import me.tauchet.lugares.dto.LugarUsuarioDTO;
import me.tauchet.lugares.dto.MiLugarDTO;
import me.tauchet.lugares.entidad.*;
import me.tauchet.lugares.excepciones.ServicioExcepcion;
import me.tauchet.lugares.proyeccion.ComentarioBase;
import me.tauchet.lugares.proyeccion.FavoritoSimple;
import me.tauchet.lugares.proyeccion.LugarBase;
import me.tauchet.lugares.repositorio.*;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.stereotype.Service;

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
    private final FavoritoRepositorio favoritoRepositorio;
    private final ComentarioRepositorio comentarioRepositorio;

    private final ProjectionFactory projectionFactory;

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
    public List<LugarBase> buscarLugaresEsperando() {
        return lugarRepositorio.buscarLugaresPorEstado(LugarEstado.ESPERANDO, LugarBase.class);
    }

    @Override
    public List<MiLugarDTO> buscarLugaresPorUsuario(int usuarioId) {
        return lugarRepositorio.buscarLugaresPorUsuario(usuarioId);
    }

    @Override
    public <T extends LugarBase> T buscarLugarPorId(int lugarId, Class<T> clase) {
        return lugarRepositorio.buscarLugarPorId(lugarId, clase);
    }

    @Override
    public void confirmarLugar(int lugarId, int usuarioId, boolean aprobado) throws ServicioExcepcion {

        LugarEstado estado = lugarRepositorio.buscarEstadoPorId(lugarId);
        if (estado != LugarEstado.ESPERANDO) {
            throw new ServicioExcepcion("¡Este lugar ya ha sido cambiado su estado!");
        }

        Optional<Lugar> lugar = lugarRepositorio.findById(lugarId);
        if (lugar.isEmpty()) {
            throw new ServicioExcepcion("¡El lugar solicitado no existe!");
        }

        Optional<Usuario> usuario = usuarioRepositorio.findById(usuarioId);
        if (usuario.isEmpty()) {
            throw new ServicioExcepcion("¡Este usuario no es un moderador!");
        }

        Lugar lugarResultado = lugar.get();
        lugarResultado.setEstado(aprobado ? LugarEstado.APROBADO : LugarEstado.DESAPROBADO);
        lugarResultado.setModerador(usuario.get());
        lugarResultado.setFechaAprobacion(new Date());
        lugarRepositorio.save(lugarResultado);

    }

    @Override
    public void registrarFavorito(int usuarioId, int lugarId) throws ServicioExcepcion {

        Optional<FavoritoSimple> validacion = favoritoRepositorio.buscarFavorito(usuarioId, lugarId, FavoritoSimple.class);
        if (validacion.isPresent()) {
            throw new ServicioExcepcion("¡Ya se ha registrado este lugar como favorito!");
        }

        Favorito favorito = new Favorito();

        // Guardar Usuario
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        favorito.setUsuario(usuario);

        // Guardar Lugar
        Lugar lugar = new Lugar();
        lugar.setId(lugarId);
        favorito.setLugar(lugar);

        favoritoRepositorio.save(favorito);

    }

    @Override
    public void eliminarFavorito(int usuarioId, int lugarId) throws ServicioExcepcion {

        Optional<FavoritoSimple> validacion = favoritoRepositorio.buscarFavorito(usuarioId, lugarId, FavoritoSimple.class);
        if (validacion.isEmpty()) {
            throw new ServicioExcepcion("¡No se ha registrado este lugar como favorito!");
        }

        favoritoRepositorio.eliminarFavorito(usuarioId, lugarId);

    }

    @Override
    public <T extends LugarBase> LugarSimpleUsuarioDTO<T> buscarLugarPorUsuario(int lugarId, int usuarioId, Class<T> clase) throws ServicioExcepcion {
        LugarSimpleUsuarioDTO<T> busqueda = null;
        if (usuarioId >= 0) {

            LugarUsuarioDTO resultado = lugarRepositorio.buscarLugarPorUsuario(lugarId, usuarioId);

            if (resultado.getLugar() == null) {
                throw new ServicioExcepcion("¡No existe el lugar que deseas buscar!");
            }

            T esperando = projectionFactory.createProjection(clase, resultado.getLugar());
            return new LugarSimpleUsuarioDTO<>(esperando, resultado.isFavorito(), resultado.isComentario());

        } else {

            T lugar = buscarLugarPorId(lugarId, clase);
            if (lugar == null) {
                throw new ServicioExcepcion("¡No existe el lugar que deseas buscar!");
            }

            busqueda = new LugarSimpleUsuarioDTO<>(lugar, false, false);
        }
        return busqueda;
    }

    @Override
    public ComentarioBase registrarComentario(ComentarioBuilder builder) throws ServicioExcepcion {

        Optional<ComentarioBase> anteriorComentario = comentarioRepositorio.buscarPorLugarYUsuario(builder.getLugarId(), builder.getUsuarioId());
        if (anteriorComentario.isPresent()) {
            throw new ServicioExcepcion("¡Ya existe un comentario en este lugar!");
        }

        Optional<Usuario> usuario = usuarioRepositorio.findById(builder.getUsuarioId());
        if (usuario.isEmpty()) {
            throw new ServicioExcepcion("¡No se ha encontrado el usuario seleccionado!");
        }

        Optional<Lugar> lugar = lugarRepositorio.findById(builder.getLugarId());
        if (lugar.isEmpty()) {
            throw new ServicioExcepcion("¡No se ha encontrado el lugar seleccionado!");
        }

        Comentario comentario = new Comentario();
        comentario.setCalificacion(builder.getCalificacion());
        comentario.setTexto(builder.getTexto());
        comentario.setLugar(lugar.get());
        comentario.setUsuario(usuario.get());
        comentario.setFechaComentario(new Date());
        comentario = comentarioRepositorio.save(comentario);

        return projectionFactory.createProjection(ComentarioBase.class, comentario);
    }

    @Override
    public void responderComentario(int usuarioId, int comentarioId, String respuesta) throws ServicioExcepcion {

        Optional<Comentario> comentario = comentarioRepositorio.findById(comentarioId);
        if (comentario.isEmpty()) {
            throw new ServicioExcepcion("¡No se ha encontrado el comentario que desea responder!");
        }

        Comentario resultado = comentario.get();
        if (resultado.getRespuesta() != null) {
            throw new ServicioExcepcion("¡Ya se ha respondido a este comentario!");
        }

        if (resultado.getLugar().getUsuario().getId() != usuarioId) {
            throw new ServicioExcepcion("¡No tienes permisos suficientes para responder a este comentario!");
        }

        resultado.setRespuesta(respuesta);
        comentarioRepositorio.save(resultado);

    }

}
