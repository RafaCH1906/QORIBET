package org.ide.qoribet.promocion.controller;

import org.ide.qoribet.promocion.dto.PromocionDTO;
import org.ide.qoribet.promocion.service.PromocionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

@RestController
@RequestMapping("/promociones")
public class PromocionController {
    private final PromocionService promocionService;

    public PromocionController(PromocionService promocionService) {
        this.promocionService = promocionService;
    }

    // Crear promoción
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PromocionDTO> crearPromocion(@RequestBody PromocionDTO dto) {
        PromocionDTO nuevaPromocion = promocionService.crearPromocion(dto);
        return ResponseEntity.ok(nuevaPromocion);
    }

    // Obtener promoción por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MODERADOR')")
    public ResponseEntity<PromocionDTO> obtenerPromocion(@PathVariable Long id) {
        PromocionDTO promocion = promocionService.obtenerPromocionPorId(id);
        return ResponseEntity.ok(promocion);
    }

    // Listar todas las promociones
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MODERADOR')")
    public ResponseEntity<List<PromocionDTO>> listarPromociones() {
        List<PromocionDTO> promociones = promocionService.listarPromociones();
        return ResponseEntity.ok(promociones);
    }

    // Actualizar promoción
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PromocionDTO> actualizarPromocion(@PathVariable Long id, @RequestBody PromocionDTO dto) {
        PromocionDTO actualizada = promocionService.actualizarPromocion(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    // Eliminar promoción
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarPromocion(@PathVariable Long id) {
        promocionService.eliminarPromocion(id);
        return ResponseEntity.noContent().build();
    }
}
