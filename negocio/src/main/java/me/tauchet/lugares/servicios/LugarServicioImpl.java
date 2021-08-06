package me.tauchet.lugares.servicios;

import lombok.RequiredArgsConstructor;
import me.tauchet.lugares.builders.ComentarioBuilder;
import me.tauchet.lugares.builders.LugarBuilder;
import me.tauchet.lugares.dto.LugarSimpleUsuarioDTO;
import me.tauchet.lugares.dto.LugarUsuarioDTO;
import me.tauchet.lugares.dto.MiLugarDTO;
import me.tauchet.lugares.entidad.*;
import me.tauchet.lugares.excepciones.ControladaExcepcion;
import me.tauchet.lugares.excepciones.ParametrosExcepcion;
import me.tauchet.lugares.excepciones.ServicioExcepcion;
import me.tauchet.lugares.proyeccion.*;
import me.tauchet.lugares.repositorio.*;
import me.tauchet.lugares.utils.ValidacionUtil;
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
    private final ImagenRepositorio imagenRepositorio;
    private final TelefonoRepositorio telefonoRepositorio;
    private final FavoritoRepositorio favoritoRepositorio;
    private final ComentarioRepositorio comentarioRepositorio;

    private final ProjectionFactory projectionFactory;

    @Override
    public LugarBase registrarLugar(LugarBuilder builder) throws ServicioExcepcion, ParametrosExcepcion {

        ValidacionUtil.estaVacio("nombre", builder.getNombre());
        ValidacionUtil.estaVacio("descripcion", builder.getDescripcion());
        ValidacionUtil.estaVacio("categoriaId", builder.getCategoriaId());
        ValidacionUtil.estaVacio("ciudadId", builder.getCiudadId());
        ValidacionUtil.estaVacio("usuarioId", builder.getUsuarioId());
        ValidacionUtil.estaVacio("latitud", builder.getLatitud());
        ValidacionUtil.estaVacio("longitud", builder.getLongitud());

        ValidacionUtil.esNecesarioEnLista("telefonos", builder.getTelefonos(), 1, "telefono");
        ValidacionUtil.esNecesarioEnLista("horarios", builder.getHorarios(), 1, "horario");
        ValidacionUtil.esNecesarioEnLista("imagenes", builder.getImagenes(), 1, "imagen");

        for (Horario horario: builder.getHorarios()) {

            if (horario == null) {
                throw new ParametrosExcepcion("horarios", "¡No se pueden agregar horarios vacíos!");
            } else if (!(horario.isLunes() || horario.isMartes() || horario.isMiercoles() || horario.isJueves() || horario.isViernes() ||
                    horario.isSabado() ||
                    horario.isDomingo())) {
                throw new ParametrosExcepcion("horarios", "¡Hay un horario sin selección de dias!");
            } else if (horario.getInicioHoras() == horario.getFinalHoras() && horario.getInicioMinutos() == horario.getFinalMinutos()) {
                throw new ParametrosExcepcion("horarios", "¡Hay un horario sin tiempos seleccionados!");
            } else if (horario.getInicioHoras() < 0 || horario.getInicioHoras() > 24 ||
                    horario.getFinalHoras() < 0 || horario.getFinalHoras() > 24 ||
                    horario.getInicioMinutos() < 0 || horario.getInicioMinutos() > 59 ||
                    horario.getFinalMinutos() < 0 || horario.getFinalMinutos() > 59) {
                throw new ParametrosExcepcion("horarios", "¡Hay un horario con tiempos fuera de lugar!");
            }

            // Reformandos horas.
            int tempA = Math.min(horario.getInicioHoras(), horario.getFinalHoras());
            int tempB = Math.max(horario.getInicioHoras(), horario.getFinalHoras());
            horario.setInicioHoras(tempA);
            horario.setFinalHoras(tempB);

        }

        Optional<Ciudad> ciudad = this.ciudadRepositorio.findById(builder.getCiudadId());
        if (ciudad.isEmpty()) {
            throw new ServicioExcepcion("¡No se ha encontrado la ciudad seleccionada!");
        }

        Optional<Categoria> categoria = this.categoriaRepositorio.findById(builder.getCategoriaId());
        if (categoria.isEmpty()) {
            throw new ServicioExcepcion("¡No se ha encontrado la categoria seleccionada!");
        }

        Optional<Usuario> usuario = this.usuarioRepositorio.findById(builder.getUsuarioId());
        if (usuario.isEmpty()) {
            throw new ServicioExcepcion("¡No se ha encontrado el usuario registrado!");
        }

        Lugar lugar = new Lugar();
        lugar.setFechaCreacion(new Date());
        lugar.setNombre(builder.getNombre());
        lugar.setDescripcion(builder.getDescripcion());
        lugar.setCategoria(categoria.get());
        lugar.setUsuario(usuario.get());
        lugar.setCiudad(ciudad.get());
        lugar.setLongitud(builder.getLongitud());
        lugar.setLatitud(builder.getLatitud());
        lugar.setEstado(LugarEstado.ESPERANDO);
        lugarRepositorio.save(lugar);

        // Guardar imagenes
        for (String imagenUrl: builder.getImagenes()) {
            Imagen imagen = new Imagen();
            imagen.setLugar(lugar);
            imagen.setUrl(imagenUrl);
            imagenRepositorio.save(imagen);
        }

        // Guardar horarios
        for (Horario horario: builder.getHorarios()) {
            horario.setLugar(lugar);
            horarioRepositorio.save(horario);
        }

        // Guardar telefonos
        for (long numero: builder.getTelefonos()) {
            Telefono telefono = new Telefono();
            telefono.setNumero(numero);
            telefono.setLugar(lugar);
            telefonoRepositorio.save(telefono);
        }

        return projectionFactory.createProjection(LugarBase.class, lugar);
    }

    @Override
    public List<LugarBase> buscarTodos(int categoriaId, String texto) {
        if (categoriaId >= 0 && texto != null && texto.length() > 1) {
            return lugarRepositorio.buscarTodosPorCategoriaYNombre(categoriaId, texto, LugarBase.class);
        } else if (categoriaId >= 0) {
            return lugarRepositorio.buscarTodosPorCategoria(categoriaId, LugarBase.class);
        } else if (texto != null && texto.length() > 1) {
            return lugarRepositorio.buscarTodosPorNombre(texto, LugarBase.class);
        }
        return lugarRepositorio.buscarTodos(LugarBase.class);
    }

    @Override
    public <T extends LugarBase> List<T> buscarTodos(Class<T> clase) {
        return lugarRepositorio.buscarTodosCompletos(clase);
    }

    @Override
    public List<LugarConUsuario> buscarLugaresEsperandoORechazados(int usuarioId) {
        return lugarRepositorio.buscarLugaresEsperandoORechazados(usuarioId, LugarConUsuario.class);
    }

    @Override
    public List<LugarRegistro> buscarLugarRegistros(int usuarioId) {
        return lugarRepositorio.buscarLugarRegistros();
    }

    @Override
    public List<MiLugarDTO> buscarLugaresPorUsuario(int usuarioId) {
        return lugarRepositorio.buscarLugaresPorUsuario(usuarioId);
    }

    @Override
    public List<LugarBase> buscarFavoritosPorUsuario(int usuarioId) {
        return lugarRepositorio.buscarFavoritosPorUsuario(usuarioId);
    }

    @Override
    public <T extends LugarBase> T buscarLugarPorNombre(String nombre, Class<T> clase) throws ServicioExcepcion {
        T lugar = lugarRepositorio.buscarLugarPorNombre(nombre, clase);
        if (lugar == null) {
            throw new ServicioExcepcion("¡No existe el lugar buscado!");
        }
        return lugar;
    }

    @Override
    public <T extends LugarBase> T buscarLugarPorId(int lugarId, Class<T> clase) throws ServicioExcepcion {
        T lugar = lugarRepositorio.buscarLugarPorId(lugarId, clase);
        if (lugar == null) {
            throw new ServicioExcepcion("¡No existe el lugar buscado!");
        }
        return lugar;
    }

    @Override
    public void confirmarLugar(Integer lugarId, Integer usuarioId, Boolean aprobado) throws ServicioExcepcion, ParametrosExcepcion {

        ValidacionUtil.estaVacio("lugarId", lugarId);
        ValidacionUtil.estaVacio("usuarioId", usuarioId);
        ValidacionUtil.estaVacio("aprobado", aprobado);

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
    public void registrarFavorito(Integer usuarioId, Integer lugarId) throws ServicioExcepcion, ParametrosExcepcion {

        ValidacionUtil.estaVacio("lugarId", lugarId);
        ValidacionUtil.estaVacio("usuarioId", usuarioId);

        Optional<FavoritoSimple> validacion = favoritoRepositorio.buscarFavorito(usuarioId, lugarId, FavoritoSimple.class);
        if (validacion.isPresent()) {
            throw new ServicioExcepcion("¡Ya se ha registrado este lugar como favorito!");
        }

        Favorito favorito = new Favorito();

        // Guardar Usuario
        Optional<Usuario> validacionUsuario = usuarioRepositorio.findById(usuarioId);
        if (validacionUsuario.isEmpty()) {
            throw new ServicioExcepcion("¡El usuario no existe!");
        }

        // Guardar Lugar
        Optional<Lugar> validacionLugar = lugarRepositorio.findById(lugarId);
        if (validacionLugar.isEmpty()) {
            throw new ServicioExcepcion("¡El lugar no existe!");
        }

        favorito.setUsuario(validacionUsuario.get());
        favorito.setLugar(validacionLugar.get());

        favoritoRepositorio.save(favorito);

    }

    @Override
    public void eliminarFavorito(Integer usuarioId, Integer lugarId) throws ServicioExcepcion, ParametrosExcepcion {

        ValidacionUtil.estaVacio("lugarId", lugarId);
        ValidacionUtil.estaVacio("usuarioId", usuarioId);

        Optional<FavoritoSimple> validacion = favoritoRepositorio.buscarFavorito(usuarioId, lugarId, FavoritoSimple.class);
        if (validacion.isEmpty()) {
            throw new ServicioExcepcion("¡No se ha registrado este lugar como favorito!");
        }

        favoritoRepositorio.eliminarFavorito(usuarioId, lugarId);

    }

    @Override
    public <T extends LugarConUsuario> LugarSimpleUsuarioDTO<T> buscarLugarSegunUsuario(
            int lugarId,
            int usuarioId,
            Class<T> clase
    ) throws ControladaExcepcion, ServicioExcepcion {

        LugarSimpleUsuarioDTO<T> busqueda = null;


        Rol usuarioRol = usuarioRepositorio.buscarRolPorUsuarioId(usuarioId);

        // El jugador se encuentra conectado
        if (usuarioId >= 0) {

            LugarUsuarioDTO resultado = lugarRepositorio.buscarLugarSegunUsuario(lugarId, usuarioId);

            if (resultado == null || resultado.getLugar() == null) {
                throw new ControladaExcepcion("¡No existe el lugar que deseas buscar!");
            }

            // NOTAS: Validar que no sea un lugar de el, para que así los moderadores no puedan poner uno.
            if (usuarioRol == Rol.USUARIO && resultado.getLugar().getEstado() == LugarEstado.ESPERANDO) {
                throw new ControladaExcepcion("¡Este lugar se encuentra procesandose!");
            } else if (resultado.getLugar().getEstado() == LugarEstado.DESAPROBADO && usuarioRol != Rol.USUARIO) {
                if (usuarioId != resultado.getLugar().getUsuario().getId()) {
                    throw new ControladaExcepcion("¡No existe el lugar que deseas buscar!");
                }
            }

            T esperando = projectionFactory.createProjection(clase, resultado.getLugar());
            return new LugarSimpleUsuarioDTO<>(esperando, resultado.isFavorito(), resultado.isComentario());

        } else {

            T lugar = buscarLugarPorId(lugarId, clase);
            if (lugar == null) {
                throw new ControladaExcepcion("¡No existe el lugar que deseas buscar!");
            }

            if (usuarioRol == Rol.USUARIO && lugar.getEstado() == LugarEstado.ESPERANDO) {
                throw new ControladaExcepcion("¡Este lugar se encuentra procesandose!");
            } else if (lugar.getEstado() == LugarEstado.DESAPROBADO && usuarioRol != Rol.USUARIO) {
                if (usuarioId != lugar.getUsuario().getId()) {
                    throw new ControladaExcepcion("¡No existe el lugar que deseas buscar!");
                }
            }

            busqueda = new LugarSimpleUsuarioDTO<>(lugar, false, false);
        }
        return busqueda;
    }

    @Override
    public ComentarioBase registrarComentario(ComentarioBuilder builder) throws ServicioExcepcion, ParametrosExcepcion {

        ValidacionUtil.estaVacio("calificacion", builder.getCalificacion());
        ValidacionUtil.estaVacio("texto", builder.getTexto());
        ValidacionUtil.estaVacio("lugarId", builder.getLugarId());
        ValidacionUtil.estaVacio("usuarioId", builder.getUsuarioId());

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
    public void responderComentario(Integer usuarioId, Integer comentarioId, String respuesta) throws ServicioExcepcion, ParametrosExcepcion {

        ValidacionUtil.estaVacio("usuarioId", usuarioId);
        ValidacionUtil.estaVacio("comentarioId", comentarioId);
        ValidacionUtil.estaVacio("respuesta", respuesta);

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

    @Override
    public void eliminarLugarPorId(int lugarId) throws ServicioExcepcion {

        Optional<Lugar> lugar = lugarRepositorio.findById(lugarId);
        if (lugar.isEmpty()) {
            throw new ServicioExcepcion("¡No existe el lugar buscado!");
        }

        Lugar instancia = lugar.get();
        if (!instancia.getFavoritos().isEmpty()) {
            favoritoRepositorio.deleteAll(instancia.getFavoritos());
        }

        if (!instancia.getTelefonos().isEmpty()) {
            telefonoRepositorio.deleteAll(instancia.getTelefonos());
        }

        if (!instancia.getHorarios().isEmpty()) {
            horarioRepositorio.deleteAll(instancia.getHorarios());
        }

        if (!instancia.getComentarios().isEmpty()) {
            comentarioRepositorio.deleteAll(instancia.getComentarios());
        }

        if (!instancia.getImagenes().isEmpty()) {
            imagenRepositorio.deleteAll(instancia.getImagenes());
        }

        lugarRepositorio.delete(instancia);
    }

    @Override
    public void actualizarLugar(Integer lugarId, Lugar lugarBuild) throws ParametrosExcepcion, ServicioExcepcion {

        ValidacionUtil.estaVacio("lugarId", lugarId);

        Optional<Lugar> lugar = lugarRepositorio.findById(lugarId);
        if (lugar.isEmpty()) {
            throw new ServicioExcepcion("¡No existe el lugar buscado!");
        }

        Lugar result = lugar.get();
        if (lugarBuild.getNombre() != null) {
            result.setNombre(lugarBuild.getNombre());
        }

        if (lugarBuild.getDescripcion() != null) {
            result.setDescripcion(lugarBuild.getDescripcion());
        }

        if (lugarBuild.getEstado() != null) {
            result.setEstado(lugarBuild.getEstado());
        }

        lugarRepositorio.save(result);

    }

}
