package org.ide.qoribet.common.security;

// Manejador personalizado para accesos denegados en la seguridad de la aplicación
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class AccessDeniedHandler implements AccessDeniedHandler {
    // Este método se ejecuta cuando un usuario intenta acceder a un recurso sin los permisos necesarios
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        // Envía un error 403 (Forbidden) al cliente
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso denegado");
    }
}
