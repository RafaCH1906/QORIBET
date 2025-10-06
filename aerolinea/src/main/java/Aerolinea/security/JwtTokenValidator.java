package Aerolinea.security;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class JwtTokenValidator {

    public boolean validateToken(String token) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                return false;
            }

            String jwtToken = token.substring(7);

            String[] parts = jwtToken.split("\\.");
            if (parts.length != 3) {
                return false;
            }

            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));

            if (payload.contains("\"exp\":")) {
                String expStr = payload.substring(payload.indexOf("\"exp\":") + 6);
                expStr = expStr.substring(0, expStr.indexOf(",") != -1 ? expStr.indexOf(",") : expStr.indexOf("}"));
                long exp = Long.parseLong(expStr);
                long currentTime = System.currentTimeMillis() / 1000;

                return exp > currentTime;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUserIdFromToken(String token) {
        try {
            String jwtToken = token.substring(7); // Remover "Bearer "
            String[] parts = jwtToken.split("\\.");
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));

            // Extraer subject (user ID) del payload
            if (payload.contains("\"sub\":\"")) {
                String sub = payload.substring(payload.indexOf("\"sub\":\"") + 7);
                sub = sub.substring(0, sub.indexOf("\""));
                return sub;
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
