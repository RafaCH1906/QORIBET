package org.ide.qoribet.saldo.service;

import org.ide.qoribet.saldo.dto.OperacionSaldoDTO;
import org.ide.qoribet.saldo.entity.OperacionSaldo;
import org.ide.qoribet.saldo.entity.SaldoDigital;
import org.ide.qoribet.saldo.repository.OperacionSaldoRepository;
import org.ide.qoribet.saldo.repository.SaldoDigitalRepository;
import org.ide.qoribet.common.exception.SaldoException.SaldoNotFoundException;
import org.ide.qoribet.common.exception.SaldoException.SaldoValidationException;
import org.ide.qoribet.common.enums.saldo.TipoOperacion;
import org.ide.qoribet.common.enums.saldo.MetodoPago;
import org.ide.qoribet.common.enums.saldo.EstadoOperacion;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperacionSaldoService {
    private final OperacionSaldoRepository operacionSaldoRepository;
    private final SaldoDigitalRepository saldoDigitalRepository;

    public OperacionSaldoService(OperacionSaldoRepository operacionSaldoRepository,
                                SaldoDigitalRepository saldoDigitalRepository) {
        this.operacionSaldoRepository = operacionSaldoRepository;
        this.saldoDigitalRepository = saldoDigitalRepository;
    }

    public OperacionSaldoDTO crearOperacion(OperacionSaldoDTO dto) {
        validarOperacion(dto);
        OperacionSaldo entity = toEntity(dto);
        entity = operacionSaldoRepository.save(entity);
        return toDTO(entity);
    }

    public OperacionSaldoDTO obtenerOperacionPorId(Long id) {
        OperacionSaldo entity = operacionSaldoRepository.findById(id)
                .orElseThrow(() -> new SaldoNotFoundException("Operación de saldo no encontrada con id: " + id));
        return toDTO(entity);
    }

    public List<OperacionSaldoDTO> listarOperaciones() {
        return operacionSaldoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<OperacionSaldoDTO> listarPorSaldoDigital(Long saldoDigitalId) {
        return operacionSaldoRepository.findBySaldoDigitalId(saldoDigitalId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public OperacionSaldoDTO actualizarOperacion(Long id, OperacionSaldoDTO dto) {
        validarOperacion(dto);
        OperacionSaldo entity = operacionSaldoRepository.findById(id)
                .orElseThrow(() -> new SaldoNotFoundException("Operación de saldo no encontrada con id: " + id));

        entity.setTipo(TipoOperacion.valueOf(dto.getTipo()));
        entity.setMonto(dto.getMonto());
        entity.setSaldoAnterior(dto.getSaldoAnterior());
        entity.setMetodoPago(MetodoPago.valueOf(dto.getMetodoPago()));
        entity.setReferencia(dto.getReferencia());
        entity.setEstadoOperacion(EstadoOperacion.valueOf(dto.getEstado()));
        entity.setFechaOperacion(dto.getFechaOperacion());
        entity.setObeservacion(dto.getObservaciones());

        entity = operacionSaldoRepository.save(entity);
        return toDTO(entity);
    }

    public void eliminarOperacion(Long id) {
        OperacionSaldo entity = operacionSaldoRepository.findById(id)
                .orElseThrow(() -> new SaldoNotFoundException("Operación de saldo no encontrada con id: " + id));
        operacionSaldoRepository.delete(entity);
    }

    private void validarOperacion(OperacionSaldoDTO dto) {
        if (dto.getMonto() == null)
            throw new SaldoValidationException("El monto es obligatorio");
        if (dto.getMonto().compareTo(java.math.BigDecimal.ZERO) < 0)
            throw new SaldoValidationException("El monto no puede ser negativo");
        if (dto.getTipo() == null || dto.getTipo().isBlank())
            throw new SaldoValidationException("El tipo de operación es obligatorio");
        if (dto.getEstado() == null || dto.getEstado().isBlank())
            throw new SaldoValidationException("El estado de la operación es obligatorio");
        if (dto.getSaldoDigitalId() == null)
            throw new SaldoValidationException("El saldo digital es obligatorio");
    }

    private OperacionSaldoDTO toDTO(OperacionSaldo entity) {
        OperacionSaldoDTO dto = new OperacionSaldoDTO();
        dto.setId(entity.getId());
        dto.setSaldoDigitalId(entity.getSaldoDigital().getId());
        dto.setTipo(entity.getTipo().name());
        dto.setMonto(entity.getMonto());
        dto.setSaldoAnterior(entity.getSaldoAnterior());
        // Calcular saldoNuevo basado en el tipo de operación
        if (entity.getTipo() == TipoOperacion.DEPOSITO) {
            dto.setSaldoNuevo(entity.getSaldoAnterior().add(entity.getMonto()));
        } else {
            dto.setSaldoNuevo(entity.getSaldoAnterior().subtract(entity.getMonto()));
        }
        dto.setMetodoPago(entity.getMetodoPago().name());
        dto.setReferencia(entity.getReferencia());
        dto.setEstado(entity.getEstadoOperacion().name());
        dto.setFechaOperacion(entity.getFechaOperacion());
        dto.setObservaciones(entity.getObeservacion());
        return dto;
    }

    private OperacionSaldo toEntity(OperacionSaldoDTO dto) {
        OperacionSaldo entity = new OperacionSaldo();

        // Buscar y asignar la entidad SaldoDigital
        SaldoDigital saldoDigital = saldoDigitalRepository.findById(dto.getSaldoDigitalId())
                .orElseThrow(() -> new SaldoNotFoundException("Saldo digital no encontrado"));
        entity.setSaldoDigital(saldoDigital);

        entity.setTipo(TipoOperacion.valueOf(dto.getTipo()));
        entity.setMonto(dto.getMonto());
        entity.setSaldoAnterior(dto.getSaldoAnterior());
        entity.setMetodoPago(MetodoPago.valueOf(dto.getMetodoPago()));
        entity.setReferencia(dto.getReferencia());
        entity.setEstadoOperacion(EstadoOperacion.valueOf(dto.getEstado()));
        entity.setFechaOperacion(dto.getFechaOperacion());
        entity.setObeservacion(dto.getObservaciones());

        return entity;
    }
}
