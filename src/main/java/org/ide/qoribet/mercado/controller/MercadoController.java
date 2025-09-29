package org.ide.qoribet.mercado.controller;

import org.ide.qoribet.mercado.dto.MercadoDTO;
import org.ide.qoribet.mercado.service.MercadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/mercado/mercados")
public class MercadoController {
    private final MercadoService mercadoService;

    public MercadoController(MercadoService mercadoService) {
        this.mercadoService = mercadoService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR')")
    public ResponseEntity<MercadoDTO> crearMercado(@RequestBody MercadoDTO mercadoDTO) {
        MercadoDTO nuevoMercado = mercadoService.crearMercado(mercadoDTO);
        return new ResponseEntity<>(nuevoMercado, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR') or hasRole('JUGADOR')")
    public ResponseEntity<MercadoDTO> obtenerMercado(@PathVariable Long id) {
        MercadoDTO mercado = mercadoService.obtenerMercadoPorId(id);
        return ResponseEntity.ok(mercado);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR') or hasRole('JUGADOR')")
    public ResponseEntity<List<MercadoDTO>> listarMercados() {
        List<MercadoDTO> mercados = mercadoService.listarMercados();
        return ResponseEntity.ok(mercados);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR')")
    public ResponseEntity<MercadoDTO> actualizarMercado(@PathVariable Long id, @RequestBody MercadoDTO mercadoDTO) {
        MercadoDTO mercadoActualizado = mercadoService.actualizarMercado(id, mercadoDTO);
        return ResponseEntity.ok(mercadoActualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarMercado(@PathVariable Long id) {
        mercadoService.eliminarMercado(id);
        return ResponseEntity.noContent().build();
    }
}
