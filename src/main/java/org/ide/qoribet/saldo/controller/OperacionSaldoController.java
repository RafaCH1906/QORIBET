package org.ide.qoribet.saldo.controller;

import org.ide.qoribet.saldo.dto.OperacionSaldoDTO;
import org.ide.qoribet.saldo.service.OperacionSaldoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

@RestController
@RequestMapping("/operaciones-saldo")
public class OperacionSaldoController {
    private final OperacionSaldoService operacionSaldoService;

    public OperacionSaldoController(OperacionSaldoService operacionSaldoService) {
        this.operacionSaldoService = operacionSaldoService;
    }

    // Crear operación de saldo
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','JUGADOR','MODERADOR')")
    public ResponseEntity<OperacionSaldoDTO> crearOperacion(@RequestBody OperacionSaldoDTO dto) {
        OperacionSaldoDTO nuevaOperacion = operacionSaldoService.crearOperacion(dto);
        return ResponseEntity.ok(nuevaOperacion);
    }

    // Obtener operación por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','JUGADOR','MODERADOR')")
    public ResponseEntity<OperacionSaldoDTO> obtenerOperacion(@PathVariable Long id) {
        OperacionSaldoDTO operacion = operacionSaldoService.obtenerOperacionPorId(id);
        return ResponseEntity.ok(operacion);
    }

    // Listar todas las operaciones
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OperacionSaldoDTO>> listarOperaciones() {
        List<OperacionSaldoDTO> operaciones = operacionSaldoService.listarOperaciones();
        return ResponseEntity.ok(operaciones);
    }

    // Listar operaciones por saldo digital
    @GetMapping("/saldo/{saldoDigitalId}")
    @PreAuthorize("hasAnyRole('ADMIN','JUGADOR','MODERADOR')")
    public ResponseEntity<List<OperacionSaldoDTO>> listarPorSaldoDigital(@PathVariable Long saldoDigitalId) {
        List<OperacionSaldoDTO> operaciones = operacionSaldoService.listarPorSaldoDigital(saldoDigitalId);
        return ResponseEntity.ok(operaciones);
    }

    // Actualizar operación de saldo
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','JUGADOR','MODERADOR')")
    public ResponseEntity<OperacionSaldoDTO> actualizarOperacion(@PathVariable Long id, @RequestBody OperacionSaldoDTO dto) {
        OperacionSaldoDTO actualizada = operacionSaldoService.actualizarOperacion(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    // Eliminar operación de saldo
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarOperacion(@PathVariable Long id) {
        operacionSaldoService.eliminarOperacion(id);
        return ResponseEntity.noContent().build();
    }
}

