package me.tauchet.lugares.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import me.tauchet.lugares.entidad.Rol;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    public static final long TOKEN_VIVO = 86400;
    public static final String TOKEN_SECRETO = "Universal";

    public int getUsuarioId(String token) {
        return Integer.parseInt(obtenerDatos(token).getSubject());
    }

    private Claims obtenerDatos(String token) {
        return Jwts.parser().setSigningKey(TOKEN_SECRETO).parseClaimsJws(token).getBody();
    }

    public String generarToken(int usuarioId, Rol rol) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", rol);

        JwtBuilder builder = Jwts.builder();
        builder.setClaims(claims);
        builder.setSubject(usuarioId + "");
        builder.setIssuedAt(new Date(System.currentTimeMillis()));
        builder.setExpiration(new Date(System.currentTimeMillis() + (TOKEN_VIVO * 1000L)));
        builder.signWith(SignatureAlgorithm.HS512, TOKEN_SECRETO);

        return builder.compact();
    }

}
