package me.tauchet.lugares.filtros;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import me.tauchet.lugares.respuestas.RestAuthRespuesta;
import me.tauchet.lugares.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class JWTRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    @Order(2)
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        int usuarioId = -1;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                usuarioId = jwtTokenUtil.getUsuarioId(jwtToken);
            } catch (ExpiredJwtException ex) {
                byte[] responseToSend = restResponseBytes(new RestAuthRespuesta("¡Tu sesión a terminado! Vuelve a iniciar sesión."));
                response.setHeader("Content-Type", "application/json");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getOutputStream().write(responseToSend);
                return;
            } catch (Exception ex) {
                byte[] responseToSend = restResponseBytes(new RestAuthRespuesta("¡Tu sesión ha fallado! Vuelve a iniciar sesión."));
                response.setHeader("Content-Type", "application/json");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getOutputStream().write(responseToSend);
                return;
            }
        }

        request.setAttribute("usuarioId", usuarioId);
        filterChain.doFilter(request, response);

    }

    private byte[] restResponseBytes(RestAuthRespuesta respuesta) throws JsonProcessingException {
        String serialized = new ObjectMapper().writeValueAsString(respuesta);
        return serialized.getBytes();
    }

}
