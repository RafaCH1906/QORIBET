package org.ide.qoribet.partido.controller;

import org.ide.qoribet.partido.dto.PartidoDTO;
import org.ide.qoribet.partido.service.PartidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

@RestController
@RequestMapping("/partidos")
public class PartidoController {
    private final PartidoService partidoService;

    public PartidoController(PartidoService partidoService) {
        this.partidoService = partidoService;
    }

    // Crear partido
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PartidoDTO> crearPartido(@RequestBody PartidoDTO dto) {
        PartidoDTO nuevoPartido = partidoService.crearPartido(dto);
        return ResponseEntity.ok(nuevoPartido);
    }

    // Obtener partido por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MODERADOR')")
    public ResponseEntity<PartidoDTO> obtenerPartido(@PathVariable Long id) {
        PartidoDTO partido = partidoService.obtenerPartidoPorId(id);
        return ResponseEntity.ok(partido);
    }

    // Listar todos los partidos
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MODERADOR')")
    public ResponseEntity<List<PartidoDTO>> listarPartidos() {
        List<PartidoDTO> partidos = partidoService.listarPartidos();
        return ResponseEntity.ok(partidos);
    }

    // Actualizar partido
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PartidoDTO> actualizarPartido(@PathVariable Long id, @RequestBody PartidoDTO dto) {
        PartidoDTO actualizado = partidoService.actualizarPartido(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar partido
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarPartido(@PathVariable Long id) {
        partidoService.eliminarPartido(id);
        return ResponseEntity.noContent().build();
    }
}
