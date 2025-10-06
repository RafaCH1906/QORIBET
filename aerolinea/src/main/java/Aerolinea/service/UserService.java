package Aerolinea.service;

import Aerolinea.dto.RegisterUserDTO;
import Aerolinea.entity.User;
import Aerolinea.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserRepository userRepository;
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile(".*[A-Z].*");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$");

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validarUsuario(RegisterUserDTO userRequest) {
        // Validar campos requeridos
        if (userRequest.getFirstName() == null || userRequest.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (userRequest.getLastName() == null || userRequest.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido es obligatorio");
        }
        if (userRequest.getEmail() == null || userRequest.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        if (userRequest.getPassword() == null || userRequest.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        // Validar que nombre tenga al menos una mayúscula
        if (!UPPERCASE_PATTERN.matcher(userRequest.getFirstName()).matches()) {
            throw new IllegalArgumentException("El nombre debe contener al menos una letra mayúscula (A-Z)");
        }

        // Validar que apellido tenga al menos una mayúscula
        if (!UPPERCASE_PATTERN.matcher(userRequest.getLastName()).matches()) {
            throw new IllegalArgumentException("El apellido debe contener al menos una letra mayúscula (A-Z)");
        }

        // Validar formato de email
        if (!EMAIL_PATTERN.matcher(userRequest.getEmail()).matches()) {
            throw new IllegalArgumentException("Debe ser un email válido");
        }

        // Validar contraseña: mínimo 8 caracteres, al menos 1 letra y 1 número
        if (!PASSWORD_PATTERN.matcher(userRequest.getPassword()).matches()) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres, incluyendo al menos una letra y un número");
        }

        // Validar que el email sea único
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
    }

    public String registerUser(RegisterUserDTO userRequest) {
        validarUsuario(userRequest);

        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword()) // En producción, se debería encriptar
                .build();

        User savedUser = userRepository.save(user);
        return savedUser.getId().toString();
    }
}
