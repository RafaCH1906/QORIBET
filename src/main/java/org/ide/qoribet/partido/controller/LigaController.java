package org.ide.qoribet.partido.controller;

import org.ide.qoribet.partido.dto.LigaDTO;
import org.ide.qoribet.partido.service.LigaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

@RestController
@RequestMapping("/ligas")
public class LigaController {
    private final LigaService ligaService;

    public LigaController(LigaService ligaService) {
        this.ligaService = ligaService;
    }

    // Crear liga
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LigaDTO> crearLiga(@RequestBody LigaDTO dto) {
        LigaDTO nuevaLiga = ligaService.crearLiga(dto);
        return ResponseEntity.ok(nuevaLiga);
    }

    // Obtener liga por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MODERADOR')")
    public ResponseEntity<LigaDTO> obtenerLiga(@PathVariable Long id) {
        LigaDTO liga = ligaService.obtenerLigaPorId(id);
        return ResponseEntity.ok(liga);
    }

    // Listar todas las ligas
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MODERADOR')")
    public ResponseEntity<List<LigaDTO>> listarLigas() {
        List<LigaDTO> ligas = ligaService.listarLigas();
        return ResponseEntity.ok(ligas);
    }

    // Actualizar liga
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LigaDTO> actualizarLiga(@PathVariable Long id, @RequestBody LigaDTO dto) {
        LigaDTO actualizada = ligaService.actualizarLiga(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    // Eliminar liga
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarLiga(@PathVariable Long id) {
        ligaService.eliminarLiga(id);
        return ResponseEntity.noContent().build();
    }
}
