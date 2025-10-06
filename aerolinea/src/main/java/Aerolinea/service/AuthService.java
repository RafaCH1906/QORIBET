package Aerolinea.service;

import Aerolinea.dto.LoginDTO;
import Aerolinea.dto.AuthToken;
import Aerolinea.entity.User;
import Aerolinea.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthToken authenticate(LoginDTO loginRequest) {
        // Validar campos obligatorios
        if (loginRequest.getEmail() == null || loginRequest.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        // Buscar usuario por email
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Email desconocido");
        }

        User user = userOptional.get();

        // Validar contraseña
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        // Generar token JWT (simplificado para este ejemplo)
        String token = generateJwtToken(user);

        return new AuthToken(token);
    }

    private String generateJwtToken(User user) {
        // Implementación simplificada de JWT
        // En producción se debería usar una librería como jjwt
        String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        String payload = "{\"sub\":\"" + user.getId() + "\",\"email\":\"" + user.getEmail() + "\",\"exp\":" + (System.currentTimeMillis() / 1000 + 3600) + "}";

        String encodedHeader = Base64.getUrlEncoder().withoutPadding().encodeToString(header.getBytes());
        String encodedPayload = Base64.getUrlEncoder().withoutPadding().encodeToString(payload.getBytes());

        // En producción, aquí se firmaría con una clave secreta
        String signature = Base64.getUrlEncoder().withoutPadding().encodeToString("secret-signature".getBytes());

        return encodedHeader + "." + encodedPayload + "." + signature;
    }
}
