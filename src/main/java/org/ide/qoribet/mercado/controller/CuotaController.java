package org.ide.qoribet.mercado.controller;

import org.ide.qoribet.mercado.dto.CuotaDTO;
import org.ide.qoribet.mercado.service.CuotaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/mercado/cuotas")
public class CuotaController {
    private final CuotaService cuotaService;

    public CuotaController(CuotaService cuotaService) {
        this.cuotaService = cuotaService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR')")
    public ResponseEntity<CuotaDTO> crearCuota(@RequestBody CuotaDTO cuotaDTO) {
        CuotaDTO nuevaCuota = cuotaService.crearCuota(cuotaDTO);
        return new ResponseEntity<>(nuevaCuota, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR') or hasRole('JUGADOR')")
    public ResponseEntity<CuotaDTO> obtenerCuota(@PathVariable Long id) {
        CuotaDTO cuota = cuotaService.obtenerCuotaPorId(id);
        return ResponseEntity.ok(cuota);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR') or hasRole('JUGADOR')")
    public ResponseEntity<List<CuotaDTO>> listarCuotas() {
        List<CuotaDTO> cuotas = cuotaService.listarCuotas();
        return ResponseEntity.ok(cuotas);
    }

    @GetMapping("/opcion/{opcionId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR') or hasRole('JUGADOR')")
    public ResponseEntity<List<CuotaDTO>> listarCuotasPorOpcion(@PathVariable Long opcionId) {
        List<CuotaDTO> cuotas = cuotaService.listarCuotasPorOpcion(opcionId);
        return ResponseEntity.ok(cuotas);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR')")
    public ResponseEntity<CuotaDTO> actualizarCuota(@PathVariable Long id, @RequestBody CuotaDTO cuotaDTO) {
        CuotaDTO cuotaActualizada = cuotaService.actualizarCuota(id, cuotaDTO);
        return ResponseEntity.ok(cuotaActualizada);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarCuota(@PathVariable Long id) {
        cuotaService.eliminarCuota(id);
        return ResponseEntity.noContent().build();
    }
}
