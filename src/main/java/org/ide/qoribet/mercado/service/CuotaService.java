package org.ide.qoribet.mercado.service;

import org.ide.qoribet.mercado.dto.CuotaDTO;
import org.ide.qoribet.mercado.entity.Cuota;
import org.ide.qoribet.mercado.repository.CuotaRepository;
import org.ide.qoribet.mercado.repository.OpcionRepository;
import org.ide.qoribet.common.exception.MercadoException.MercadoNotFoundException;
import org.ide.qoribet.common.exception.MercadoException.MercadoValidationException;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuotaService {
    private final CuotaRepository cuotaRepository;
    private final OpcionRepository opcionRepository;

    public CuotaService(CuotaRepository cuotaRepository, OpcionRepository opcionRepository) {
        this.cuotaRepository = cuotaRepository;
        this.opcionRepository = opcionRepository;
    }

    public CuotaDTO crearCuota(CuotaDTO dto) {
        validarCuota(dto);
        Cuota entity = toEntity(dto);
        entity.setCreadaEn(LocalDateTime.now());
        entity = cuotaRepository.save(entity);
        return toDTO(entity);
    }

    public CuotaDTO obtenerCuotaPorId(Long id) {
        Cuota entity = cuotaRepository.findById(id)
                .orElseThrow(() -> new MercadoNotFoundException("Cuota no encontrada con id: " + id));
        return toDTO(entity);
    }

    public List<CuotaDTO> listarCuotas() {
        return cuotaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<CuotaDTO> listarCuotasPorOpcion(Long opcionId) {
        return cuotaRepository.findByOpcionId(opcionId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CuotaDTO actualizarCuota(Long id, CuotaDTO dto) {
        validarCuota(dto);
        Cuota entity = cuotaRepository.findById(id)
                .orElseThrow(() -> new MercadoNotFoundException("Cuota no encontrada con id: " + id));
        entity.setValor(dto.getValor().doubleValue());
        entity.setOpcion(opcionRepository.findById(dto.getOpcionId().longValue())
                .orElseThrow(() -> new MercadoValidationException("Opción no encontrada")));
        entity = cuotaRepository.save(entity);
        return toDTO(entity);
    }

    public void eliminarCuota(Long id) {
        Cuota entity = cuotaRepository.findById(id)
                .orElseThrow(() -> new MercadoNotFoundException("Cuota no encontrada con id: " + id));
        cuotaRepository.delete(entity);
    }

    private void validarCuota(CuotaDTO dto) {
        if (dto.getValor() == null)
            throw new MercadoValidationException("El valor de la cuota es obligatorio");
        if (dto.getValor().compareTo(BigDecimal.ZERO) <= 0)
            throw new MercadoValidationException("El valor de la cuota debe ser positivo");
        if (dto.getOpcionId() == null)
            throw new MercadoValidationException("La opción es obligatoria");
    }

    private CuotaDTO toDTO(Cuota entity) {
        CuotaDTO dto = new CuotaDTO();
        dto.setId(entity.getId() != null ? entity.getId().intValue() : null);
        dto.setOpcionId(entity.getOpcion() != null ? entity.getOpcion().getId().intValue() : null);
        dto.setValor(BigDecimal.valueOf(entity.getValor()));
        dto.setCreadaEn(entity.getCreadaEn());
        return dto;
    }

    private Cuota toEntity(CuotaDTO dto) {
        Cuota entity = new Cuota();
        entity.setValor(dto.getValor().doubleValue());
        entity.setOpcion(opcionRepository.findById(dto.getOpcionId().longValue())
                .orElseThrow(() -> new MercadoValidationException("Opción no encontrada")));
        return entity;
    }
}
