package org.ide.qoribet.partido.controller;

import org.ide.qoribet.partido.dto.EquipoDTO;
import org.ide.qoribet.partido.service.EquipoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

@RestController
@RequestMapping("/equipos")
public class EquipoController {
    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    // Crear equipo
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EquipoDTO> crearEquipo(@RequestBody EquipoDTO dto) {
        EquipoDTO nuevoEquipo = equipoService.crearEquipo(dto);
        return ResponseEntity.ok(nuevoEquipo);
    }

    // Obtener equipo por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MODERADOR')")
    public ResponseEntity<EquipoDTO> obtenerEquipo(@PathVariable Long id) {
        EquipoDTO equipo = equipoService.obtenerEquipoPorId(id);
        return ResponseEntity.ok(equipo);
    }

    // Listar todos los equipos
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MODERADOR')")
    public ResponseEntity<List<EquipoDTO>> listarEquipos() {
        List<EquipoDTO> equipos = equipoService.listarEquipos();
        return ResponseEntity.ok(equipos);
    }

    // Actualizar equipo
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EquipoDTO> actualizarEquipo(@PathVariable Long id, @RequestBody EquipoDTO dto) {
        EquipoDTO actualizado = equipoService.actualizarEquipo(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar equipo
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarEquipo(@PathVariable Long id) {
        equipoService.eliminarEquipo(id);
        return ResponseEntity.noContent().build();
    }
}
