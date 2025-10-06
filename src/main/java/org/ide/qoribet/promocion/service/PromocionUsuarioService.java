package org.ide.qoribet.promocion.service;

import org.ide.qoribet.promocion.dto.PromocionUsuarioDTO;
import org.ide.qoribet.promocion.entity.PromocionUsuario;
import org.ide.qoribet.promocion.entity.Promocion;
import org.ide.qoribet.promocion.repository.PromocionUsuarioRepository;
import org.ide.qoribet.promocion.repository.PromocionRepository;
import org.ide.qoribet.usuario.repository.UsuarioRepository;
import org.ide.qoribet.usuario.entity.Usuario;
import org.ide.qoribet.common.exception.PromocionException.PromocionNotFoundException;
import org.ide.qoribet.common.exception.PromocionException.PromocionValidationException;
import org.ide.qoribet.common.exception.UsuarioException.UsuarioNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromocionUsuarioService {
    private final PromocionUsuarioRepository promocionUsuarioRepository;
    private final PromocionRepository promocionRepository;
    private final UsuarioRepository usuarioRepository;

    public PromocionUsuarioService(PromocionUsuarioRepository promocionUsuarioRepository,
                                   PromocionRepository promocionRepository,
                                   UsuarioRepository usuarioRepository) {
        this.promocionUsuarioRepository = promocionUsuarioRepository;
        this.promocionRepository = promocionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public PromocionUsuarioDTO asignarPromocion(PromocionUsuarioDTO dto) {
        validarPromocionUsuario(dto);
        Promocion promocion = promocionRepository.findById(dto.getPromocionId())
                .orElseThrow(() -> new PromocionNotFoundException("Promoción no encontrada"));
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));
        boolean yaAsignada = promocionUsuarioRepository.findByUsuarioId(dto.getUsuarioId()).stream()
                .anyMatch(pu -> pu.getPromocion().getId().equals(dto.getPromocionId()));
        if (yaAsignada) {
            throw new PromocionValidationException("La promoción ya está asignada a este usuario");
        }
        PromocionUsuario entity = toEntity(dto, promocion, usuario);
        entity = promocionUsuarioRepository.save(entity);
        return toDTO(entity);
    }

    public PromocionUsuarioDTO obtenerPorId(Long id) {
        PromocionUsuario entity = promocionUsuarioRepository.findById(id)
                .orElseThrow(() -> new PromocionNotFoundException("Promoción-usuario no encontrada con id: " + id));
        return toDTO(entity);
    }

    public List<PromocionUsuarioDTO> listarPorUsuario(Long usuarioId) {
        return promocionUsuarioRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PromocionUsuarioDTO> listarPorPromocion(Long promocionId) {
        return promocionUsuarioRepository.findByPromocionId(promocionId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PromocionUsuarioDTO actualizarPromocionUsuario(Long id, PromocionUsuarioDTO dto) {
        validarPromocionUsuario(dto);
        PromocionUsuario entity = promocionUsuarioRepository.findById(id)
                .orElseThrow(() -> new PromocionNotFoundException("Promoción-usuario no encontrada con id: " + id));
        entity.setMontoAplicado(dto.getMontoAplicado());
        entity.setBeneficioOtorgado(dto.getBeneficioOtorgado());
        entity.setFechaAplicacion(dto.getFechaAplicacion());
        entity.setUtilizado(dto.isUtilizado());
        entity = promocionUsuarioRepository.save(entity);
        return toDTO(entity);
    }

    public void eliminarPromocionUsuario(Long id) {
        PromocionUsuario entity = promocionUsuarioRepository.findById(id)
                .orElseThrow(() -> new PromocionNotFoundException("Promoción-usuario no encontrada con id: " + id));
        promocionUsuarioRepository.delete(entity);
    }

    private void validarPromocionUsuario(PromocionUsuarioDTO dto) {
        if (dto.getUsuarioId() == null)
            throw new PromocionValidationException("El usuario es obligatorio");
        if (dto.getPromocionId() == null)
            throw new PromocionValidationException("La promoción es obligatoria");
    }

    private PromocionUsuarioDTO toDTO(PromocionUsuario entity) {
        PromocionUsuarioDTO dto = new PromocionUsuarioDTO();
        dto.setId(entity.getId());
        dto.setUsuarioId(entity.getUsuario().getId());
        dto.setPromocionId(entity.getPromocion().getId());
        dto.setFechaAsignacion(entity.getFechaAsignacion());
        dto.setUsada(entity.getUsada());
        dto.setMontoAplicado(entity.getMontoAplicado());
        dto.setBeneficioOtorgado(entity.getBeneficioOtorgado());
        dto.setFechaAplicacion(entity.getFechaAplicacion());
        dto.setUtilizado(entity.getUtilizado());
        return dto;
    }

    private PromocionUsuario toEntity(PromocionUsuarioDTO dto, Promocion promocion, Usuario usuario) {
        PromocionUsuario entity = new PromocionUsuario();
        entity.setUsuario(usuario);
        entity.setPromocion(promocion);
        entity.setFechaAsignacion(dto.getFechaAsignacion());
        entity.setUsada(dto.getUsada());
        entity.setMontoAplicado(dto.getMontoAplicado());
        entity.setBeneficioOtorgado(dto.getBeneficioOtorgado());
        entity.setFechaAplicacion(dto.getFechaAplicacion());
        entity.setUtilizado(dto.isUtilizado());
        return entity;
    }
}
