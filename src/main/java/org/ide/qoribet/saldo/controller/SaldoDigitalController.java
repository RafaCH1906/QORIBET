package org.ide.qoribet.saldo.controller;

import org.ide.qoribet.saldo.dto.SaldoDigitalDTO;
import org.ide.qoribet.saldo.service.SaldoDigitalService;
import org.ide.qoribet.saldo.service.SaldoAsyncService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

@RestController
@RequestMapping("/saldos")
public class SaldoDigitalController {
    private final SaldoDigitalService saldoDigitalService;
    private final SaldoAsyncService saldoAsyncService;

    public SaldoDigitalController(SaldoDigitalService saldoDigitalService, SaldoAsyncService saldoAsyncService) {
        this.saldoDigitalService = saldoDigitalService;
        this.saldoAsyncService = saldoAsyncService;
    }

    // Crear saldo digital
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','JUGADOR','MODERADOR')")
    public ResponseEntity<SaldoDigitalDTO> crearSaldo(@RequestBody SaldoDigitalDTO saldoDigital) {
        SaldoDigitalDTO nuevoSaldo = saldoDigitalService.crearSaldo(saldoDigital);
        return ResponseEntity.ok(nuevoSaldo);
    }

    //  Procesar depósito con procesamiento asíncrono
    @PostMapping("/{id}/deposito")
    @PreAuthorize("hasAnyRole('ADMIN','JUGADOR','MODERADOR')")
    public ResponseEntity<String> procesarDeposito(@PathVariable Long id,
                                                  @RequestParam String metodoPago,
                                                  @RequestParam String monto) {
        // Respuesta inmediata al usuario
        saldoAsyncService.procesarDepositoMasivo(id, new java.math.BigDecimal(monto), metodoPago);
        return ResponseEntity.ok("Depósito iniciado. Recibirás una notificación cuando se complete.");
    }

    //  Procesar retiro con validaciones complejas asíncronas
    @PostMapping("/{id}/retiro")
    @PreAuthorize("hasAnyRole('ADMIN','JUGADOR','MODERADOR')")
    public ResponseEntity<String> procesarRetiro(@PathVariable Long id, @RequestParam String monto) {
        saldoAsyncService.procesarRetiroComplejo(id, new java.math.BigDecimal(monto));
        return ResponseEntity.ok("Retiro en proceso. Te notificaremos cuando esté listo.");
    }

    // Obtener saldo por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','JUGADOR','MODERADOR')")
    public ResponseEntity<SaldoDigitalDTO> obtenerSaldo(@PathVariable Long id) {
        SaldoDigitalDTO saldo = saldoDigitalService.obtenerSaldoPorId(id);
        return ResponseEntity.ok(saldo);
    }

    // Listar todos los saldos
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SaldoDigitalDTO>> listarSaldos() {
        List<SaldoDigitalDTO> saldos = saldoDigitalService.listarSaldos();
        return ResponseEntity.ok(saldos);
    }

    // Actualizar saldo
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','JUGADOR','MODERADOR')")
    public ResponseEntity<SaldoDigitalDTO> actualizarSaldo(@PathVariable Long id, @RequestBody SaldoDigitalDTO saldoDigital) {
        SaldoDigitalDTO actualizado = saldoDigitalService.actualizarSaldo(id, saldoDigital);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar saldo
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarSaldo(@PathVariable Long id) {
        saldoDigitalService.eliminarSaldo(id);
        return ResponseEntity.noContent().build();
    }
}
