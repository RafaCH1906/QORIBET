package org.ide.qoribet.promocion.service;

import org.ide.qoribet.promocion.dto.PromocionDTO;
import org.ide.qoribet.promocion.entity.Promocion;
import org.ide.qoribet.promocion.repository.PromocionRepository;
import org.ide.qoribet.common.enums.TipoPromocion.TipoPromocion;
import org.ide.qoribet.common.exception.PromocionException.PromocionValidationException;
import org.ide.qoribet.common.exception.PromocionException.PromocionNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromocionService {
    private final PromocionRepository promocionRepository;

    public PromocionService(PromocionRepository promocionRepository) {
        this.promocionRepository = promocionRepository;
    }

    public PromocionDTO crearPromocion(PromocionDTO dto) {
        validarPromocion(dto);
        if (promocionRepository.findByCodigo(dto.getCodigo()).isPresent()) {
            throw new PromocionValidationException("Ya existe una promoción con ese código");
        }
        Promocion entity = toEntity(dto);
        entity = promocionRepository.save(entity);
        return toDTO(entity);
    }

    public PromocionDTO obtenerPromocionPorId(Long id) {
        Promocion entity = promocionRepository.findById(id)
                .orElseThrow(() -> new PromocionNotFoundException("Promoción no encontrada con id: " + id));
        return toDTO(entity);
    }

    public List<PromocionDTO> listarPromociones() {
        return promocionRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PromocionDTO actualizarPromocion(Long id, PromocionDTO dto) {
        validarPromocion(dto);
        Promocion entity = promocionRepository.findById(id)
                .orElseThrow(() -> new PromocionNotFoundException("Promoción no encontrada con id: " + id));
        entity.setCodigo(dto.getCodigo());
        entity.setTipo(TipoPromocion.valueOf(dto.getTipo()));
        entity.setValor(dto.getValor());
        entity.setMontoMinimo(dto.getMontoMinimo());
        entity.setBeneficioMaximo(dto.getBeneficioMaximo());
        entity.setFechaInicio(dto.getFechaInicio());
        entity.setFechaExpiracion(dto.getFechaExpiracion());
        entity.setUsosMaximos(dto.getUsosMaximos());
        entity.setUsosActuales(dto.getUsosActuales());
        entity.setActivo(dto.isActivo());
        entity.setDescripcion(dto.getDescripcion());
        entity = promocionRepository.save(entity);
        return toDTO(entity);
    }

    public void eliminarPromocion(Long id) {
        Promocion entity = promocionRepository.findById(id)
                .orElseThrow(() -> new PromocionNotFoundException("Promoción no encontrada con id: " + id));
        promocionRepository.delete(entity);
    }

    private void validarPromocion(PromocionDTO dto) {
        if (dto.getCodigo() == null || dto.getCodigo().isBlank())
            throw new PromocionValidationException("El código es obligatorio");
        if (dto.getTipo() == null || dto.getTipo().isBlank())
            throw new PromocionValidationException("El tipo de promoción es obligatorio");
        if (dto.getValor() == null)
            throw new PromocionValidationException("El valor es obligatorio");
        if (dto.getFechaInicio() == null)
            throw new PromocionValidationException("La fecha de inicio es obligatoria");
        if (dto.getFechaExpiracion() == null)
            throw new PromocionValidationException("La fecha de expiración es obligatoria");
    }

    private PromocionDTO toDTO(Promocion entity) {
        PromocionDTO dto = new PromocionDTO();
        dto.setId(entity.getId());
        dto.setCodigo(entity.getCodigo());
        dto.setTipo(entity.getTipo().name());
        dto.setValor(entity.getValor());
        dto.setMontoMinimo(entity.getMontoMinimo());
        dto.setBeneficioMaximo(entity.getBeneficioMaximo());
        dto.setFechaInicio(entity.getFechaInicio());
        dto.setFechaExpiracion(entity.getFechaExpiracion());
        dto.setUsosMaximos(entity.getUsosMaximos());
        dto.setUsosActuales(entity.getUsosActuales());
        dto.setActivo(entity.getActivo());
        dto.setDescripcion(entity.getDescripcion());
        return dto;
    }

    private Promocion toEntity(PromocionDTO dto) {
        Promocion entity = new Promocion();
        entity.setId(dto.getId());
        entity.setCodigo(dto.getCodigo());
        entity.setTipo(TipoPromocion.valueOf(dto.getTipo()));
        entity.setValor(dto.getValor());
        entity.setMontoMinimo(dto.getMontoMinimo());
        entity.setBeneficioMaximo(dto.getBeneficioMaximo());
        entity.setFechaInicio(dto.getFechaInicio());
        entity.setFechaExpiracion(dto.getFechaExpiracion());
        entity.setUsosMaximos(dto.getUsosMaximos());
        entity.setUsosActuales(dto.getUsosActuales());
        entity.setActivo(dto.isActivo());
        entity.setDescripcion(dto.getDescripcion());
        return entity;
    }
}
