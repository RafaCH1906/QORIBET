package org.ide.qoribet.apuesta.controller;

import org.ide.qoribet.apuesta.dto.ApuestaDTO;
import org.ide.qoribet.apuesta.service.ApuestaService;
import org.ide.qoribet.common.enums.apuesta.EstadoApuesta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/apuestas")
public class ApuestaController {
    private final ApuestaService apuestaService;

    public ApuestaController(ApuestaService apuestaService) {
        this.apuestaService = apuestaService;
    }

    @PostMapping
    @PreAuthorize("hasRole('JUGADOR') or hasRole('ADMIN') or hasRole('MODERADOR')")
    public ResponseEntity<ApuestaDTO> crearApuesta(@RequestBody ApuestaDTO apuestaDTO) {
        ApuestaDTO nuevaApuesta = apuestaService.crearApuesta(apuestaDTO);
        return new ResponseEntity<>(nuevaApuesta, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR') or (hasRole('JUGADOR') and @apuestaService.obtenerApuestaPorId(#id).usuarioId == authentication.principal.id)")
    public ResponseEntity<ApuestaDTO> obtenerApuesta(@PathVariable Long id) {
        ApuestaDTO apuesta = apuestaService.obtenerApuestaPorId(id);
        return ResponseEntity.ok(apuesta);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR')")
    public ResponseEntity<List<ApuestaDTO>> listarApuestas() {
        List<ApuestaDTO> apuestas = apuestaService.listarApuestas();
        return ResponseEntity.ok(apuestas);
    }

    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR') or (hasRole('JUGADOR') and #usuarioId == authentication.principal.id)")
    public ResponseEntity<List<ApuestaDTO>> listarApuestasPorUsuario(@PathVariable Long usuarioId) {
        List<ApuestaDTO> apuestas = apuestaService.listarApuestasPorUsuario(usuarioId);
        return ResponseEntity.ok(apuestas);
    }

    @GetMapping("/usuario/{usuarioId}/estado/{estado}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR') or (hasRole('JUGADOR') and #usuarioId == authentication.principal.id)")
    public ResponseEntity<List<ApuestaDTO>> listarApuestasPorUsuarioYEstado(@PathVariable Long usuarioId, @PathVariable String estado) {
        List<ApuestaDTO> apuestas = apuestaService.listarApuestasPorUsuarioYEstado(usuarioId, estado);
        return ResponseEntity.ok(apuestas);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR')")
    public ResponseEntity<ApuestaDTO> actualizarApuesta(@PathVariable Long id, @RequestBody ApuestaDTO apuestaDTO) {
        ApuestaDTO apuestaActualizada = apuestaService.actualizarApuesta(id, apuestaDTO);
        return ResponseEntity.ok(apuestaActualizada);
    }

    @PutMapping("/{id}/resolver")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR')")
    public ResponseEntity<ApuestaDTO> resolverApuesta(@PathVariable Long id, @RequestParam EstadoApuesta estado) {
        ApuestaDTO apuestaResuelta = apuestaService.resolverApuesta(id, estado);
        return ResponseEntity.ok(apuestaResuelta);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarApuesta(@PathVariable Long id) {
        apuestaService.eliminarApuesta(id);
        return ResponseEntity.noContent().build();
    }
}
