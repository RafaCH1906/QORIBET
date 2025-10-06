package Aerolinea.controller;

import Aerolinea.dto.LoginDTO;
import Aerolinea.dto.AuthToken;
import Aerolinea.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthToken> login(@Valid @RequestBody LoginDTO login) {
        try {
            AuthToken token = authService.authenticate(login);
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            // Manejar errores de validación (email desconocido, contraseña incorrecta)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
