package org.ide.qoribet.mercado.controller;

import org.ide.qoribet.mercado.dto.OpcionDTO;
import org.ide.qoribet.mercado.service.OpcionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/mercado/opciones")
public class OpcionController {
    private final OpcionService opcionService;

    public OpcionController(OpcionService opcionService) {
        this.opcionService = opcionService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR')")
    public ResponseEntity<OpcionDTO> crearOpcion(@RequestBody OpcionDTO opcionDTO) {
        OpcionDTO nuevaOpcion = opcionService.crearOpcion(opcionDTO);
        return new ResponseEntity<>(nuevaOpcion, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR') or hasRole('JUGADOR')")
    public ResponseEntity<OpcionDTO> obtenerOpcion(@PathVariable Long id) {
        OpcionDTO opcion = opcionService.obtenerOpcionPorId(id);
        return ResponseEntity.ok(opcion);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR') or hasRole('JUGADOR')")
    public ResponseEntity<List<OpcionDTO>> listarOpciones() {
        List<OpcionDTO> opciones = opcionService.listarOpciones();
        return ResponseEntity.ok(opciones);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR')")
    public ResponseEntity<OpcionDTO> actualizarOpcion(@PathVariable Long id, @RequestBody OpcionDTO opcionDTO) {
        OpcionDTO opcionActualizada = opcionService.actualizarOpcion(id, opcionDTO);
        return ResponseEntity.ok(opcionActualizada);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarOpcion(@PathVariable Long id) {
        opcionService.eliminarOpcion(id);
        return ResponseEntity.noContent().build();
    }
}
