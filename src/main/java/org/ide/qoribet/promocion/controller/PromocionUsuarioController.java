package org.ide.qoribet.promocion.controller;

import org.ide.qoribet.promocion.dto.PromocionUsuarioDTO;
import org.ide.qoribet.promocion.service.PromocionUsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

@RestController
@RequestMapping("/promocion-usuario")
public class PromocionUsuarioController {
    private final PromocionUsuarioService promocionUsuarioService;

    public PromocionUsuarioController(PromocionUsuarioService promocionUsuarioService) {
        this.promocionUsuarioService = promocionUsuarioService;
    }

    // Asignar promoción a usuario
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PromocionUsuarioDTO> asignarPromocion(@RequestBody PromocionUsuarioDTO dto) {
        PromocionUsuarioDTO asignada = promocionUsuarioService.asignarPromocion(dto);
        return ResponseEntity.ok(asignada);
    }

    // Obtener promoción-usuario por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MODERADOR')")
    public ResponseEntity<PromocionUsuarioDTO> obtenerPorId(@PathVariable Long id) {
        PromocionUsuarioDTO pu = promocionUsuarioService.obtenerPorId(id);
        return ResponseEntity.ok(pu);
    }

    // Listar promociones por usuario
    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasAnyRole('ADMIN','MODERADOR')")
    public ResponseEntity<List<PromocionUsuarioDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<PromocionUsuarioDTO> lista = promocionUsuarioService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(lista);
    }

    // Listar promociones por promoción
    @GetMapping("/promocion/{promocionId}")
    @PreAuthorize("hasAnyRole('ADMIN','MODERADOR')")
    public ResponseEntity<List<PromocionUsuarioDTO>> listarPorPromocion(@PathVariable Long promocionId) {
        List<PromocionUsuarioDTO> lista = promocionUsuarioService.listarPorPromocion(promocionId);
        return ResponseEntity.ok(lista);
    }

    // Actualizar promoción-usuario
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PromocionUsuarioDTO> actualizarPromocionUsuario(@PathVariable Long id, @RequestBody PromocionUsuarioDTO dto) {
        PromocionUsuarioDTO actualizada = promocionUsuarioService.actualizarPromocionUsuario(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    // Eliminar promoción-usuario
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarPromocionUsuario(@PathVariable Long id) {
        promocionUsuarioService.eliminarPromocionUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
