package org.ide.qoribet.common.security;

// Clase utilitaria para generar y validar tokens JWT
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtTokenProvider {
    // Clave secreta para firmar el token
    @Value("${jwt.secret}")
    private String jwtSecret;

    // Tiempo de expiración del token en milisegundos
    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    // Genera un token JWT para el usuario
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    // Extrae el username del token JWT
    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    // Valida si el token JWT es correcto y no ha expirado
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
