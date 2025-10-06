package org.ide.qoribet.usuario.controller;

import org.ide.qoribet.usuario.dto.PersonaDTO;
import org.ide.qoribet.usuario.service.PersonaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

@RestController
@RequestMapping("/personas")
public class PersonaController {
    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    // Crear persona
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','JUGADOR','MODERADOR')")
    public ResponseEntity<PersonaDTO> crearPersona(@RequestBody PersonaDTO personaDTO) {
        PersonaDTO nuevaPersona = personaService.crearPersona(personaDTO);
        return ResponseEntity.ok(nuevaPersona);
    }

    // Obtener persona por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','JUGADOR','MODERADOR')")
    public ResponseEntity<PersonaDTO> obtenerPersona(@PathVariable Long id) {
        PersonaDTO persona = personaService.obtenerPersonaPorId(id);
        return ResponseEntity.ok(persona);
    }

    // Listar todas las personas
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PersonaDTO>> listarPersonas() {
        List<PersonaDTO> personas = personaService.listarPersonas();
        return ResponseEntity.ok(personas);
    }

    // Actualizar persona
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','JUGADOR','MODERADOR')")
    public ResponseEntity<PersonaDTO> actualizarPersona(@PathVariable Long id, @RequestBody PersonaDTO personaDTO) {
        PersonaDTO actualizada = personaService.actualizarPersona(id, personaDTO);
        return ResponseEntity.ok(actualizada);
    }

    // Eliminar persona
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarPersona(@PathVariable Long id) {
        personaService.eliminarPersona(id);
        return ResponseEntity.noContent().build();
    }
}

