package Aerolinea.controller;

import Aerolinea.dto.RegisterUserDTO;
import Aerolinea.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // UNPROTECTED
    @PostMapping("/register")
    public ResponseEntity<RegisterUserDTO> register(@Valid @RequestBody RegisterUserDTO newUser) {
        try {
            String userId = userService.registerUser(newUser);
            // Devolver el mismo RegisterUserDTO que se registró exitosamente
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
