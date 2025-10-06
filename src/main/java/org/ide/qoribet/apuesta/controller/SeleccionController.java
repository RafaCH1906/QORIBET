package org.ide.qoribet.apuesta.controller;

import org.ide.qoribet.apuesta.dto.SeleccionDTO;
import org.ide.qoribet.apuesta.service.SeleccionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/selecciones")
public class SeleccionController {
    private final SeleccionService seleccionService;

    public SeleccionController(SeleccionService seleccionService) {
        this.seleccionService = seleccionService;
    }

    @PostMapping
    @PreAuthorize("hasRole('JUGADOR') or hasRole('ADMIN') or hasRole('MODERADOR')")
    public ResponseEntity<SeleccionDTO> crearSeleccion(@RequestBody SeleccionDTO seleccionDTO) {
        SeleccionDTO nuevaSeleccion = seleccionService.crearSeleccion(seleccionDTO);
        return new ResponseEntity<>(nuevaSeleccion, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR') or hasRole('JUGADOR')")
    public ResponseEntity<SeleccionDTO> obtenerSeleccion(@PathVariable Long id) {
        SeleccionDTO seleccion = seleccionService.obtenerSeleccionPorId(id);
        return ResponseEntity.ok(seleccion);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR')")
    public ResponseEntity<List<SeleccionDTO>> listarSelecciones() {
        List<SeleccionDTO> selecciones = seleccionService.listarSelecciones();
        return ResponseEntity.ok(selecciones);
    }

    @GetMapping("/apuesta/{apuestaId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR') or hasRole('JUGADOR')")
    public ResponseEntity<List<SeleccionDTO>> listarSeleccionesPorApuesta(@PathVariable Long apuestaId) {
        List<SeleccionDTO> selecciones = seleccionService.listarSeleccionesPorApuesta(apuestaId);
        return ResponseEntity.ok(selecciones);
    }

    @GetMapping("/opcion/{opcionId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR') or hasRole('JUGADOR')")
    public ResponseEntity<List<SeleccionDTO>> listarSeleccionesPorOpcion(@PathVariable Long opcionId) {
        List<SeleccionDTO> selecciones = seleccionService.listarSeleccionesPorOpcion(opcionId);
        return ResponseEntity.ok(selecciones);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR')")
    public ResponseEntity<SeleccionDTO> actualizarSeleccion(@PathVariable Long id, @RequestBody SeleccionDTO seleccionDTO) {
        SeleccionDTO seleccionActualizada = seleccionService.actualizarSeleccion(id, seleccionDTO);
        return ResponseEntity.ok(seleccionActualizada);
    }

    @PutMapping("/{id}/resolver")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR')")
    public ResponseEntity<SeleccionDTO> resolverSeleccion(@PathVariable Long id, @RequestParam String resultado) {
        SeleccionDTO seleccionResuelta = seleccionService.resolverSeleccion(id, resultado);
        return ResponseEntity.ok(seleccionResuelta);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarSeleccion(@PathVariable Long id) {
        seleccionService.eliminarSeleccion(id);
        return ResponseEntity.noContent().build();
    }
}
