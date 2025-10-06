package org.ide.qoribet.saldo.service;

import org.ide.qoribet.saldo.dto.SaldoDigitalDTO;
import org.ide.qoribet.saldo.entity.SaldoDigital;
import org.ide.qoribet.saldo.repository.SaldoDigitalRepository;
import org.ide.qoribet.usuario.entity.Usuario;
import org.ide.qoribet.usuario.repository.UsuarioRepository;
import org.ide.qoribet.common.exception.SaldoException.SaldoNotFoundException;
import org.ide.qoribet.common.exception.SaldoException.SaldoValidationException;
import org.ide.qoribet.common.exception.UsuarioException.UsuarioNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaldoDigitalService {
    private final SaldoDigitalRepository saldoDigitalRepository;
    private final UsuarioRepository usuarioRepository;

    public SaldoDigitalService(SaldoDigitalRepository saldoDigitalRepository, UsuarioRepository usuarioRepository) {
        this.saldoDigitalRepository = saldoDigitalRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public SaldoDigitalDTO crearSaldo(SaldoDigitalDTO saldoDigital) {
        validarSaldo(saldoDigital);
        SaldoDigital entity = toEntity(saldoDigital);
        entity = saldoDigitalRepository.save(entity);
        return toDTO(entity);
    }

    public SaldoDigitalDTO obtenerSaldoPorId(Long id) {
        SaldoDigital entity = saldoDigitalRepository.findById(id)
                .orElseThrow(() -> new SaldoNotFoundException("Saldo no encontrado con id: " + id));
        return toDTO(entity);
    }

    public List<SaldoDigitalDTO> listarSaldos() {
        return saldoDigitalRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public SaldoDigitalDTO actualizarSaldo(Long id, SaldoDigitalDTO saldoDigital) {
        validarSaldo(saldoDigital);
        SaldoDigital entity = saldoDigitalRepository.findById(id)
                .orElseThrow(() -> new SaldoNotFoundException("Saldo no encontrado con id: " + id));
        entity.setMontoActual(saldoDigital.getMontoActual());
        entity.setUltimaActualizacion(saldoDigital.getUltimaActualizacion());
        entity = saldoDigitalRepository.save(entity);
        return toDTO(entity);
    }

    public void eliminarSaldo(Long id) {
        SaldoDigital entity = saldoDigitalRepository.findById(id)
                .orElseThrow(() -> new SaldoNotFoundException("Saldo no encontrado con id: " + id));
        saldoDigitalRepository.delete(entity);
    }

    private void validarSaldo(SaldoDigitalDTO saldoDigital) {
        if (saldoDigital.getMontoActual() == null)
            throw new SaldoValidationException("El monto actual es obligatorio");
        if (saldoDigital.getMontoActual().compareTo(java.math.BigDecimal.ZERO) < 0)
            throw new SaldoValidationException("El saldo no puede ser negativo");
        if (saldoDigital.getUsuarioId() == null)
            throw new SaldoValidationException("El usuario es obligatorio");
    }

    private SaldoDigitalDTO toDTO(SaldoDigital entity) {
        SaldoDigitalDTO dto = new SaldoDigitalDTO();
        dto.setId(entity.getId());
        dto.setUsuarioId(entity.getUsuario().getId());
        dto.setMontoActual(entity.getMontoActual());
        dto.setUltimaActualizacion(entity.getUltimaActualizacion());
        return dto;
    }

    private SaldoDigital toEntity(SaldoDigitalDTO dto) {
        SaldoDigital entity = new SaldoDigital();
        entity.setId(dto.getId());
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
            .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con id: " + dto.getUsuarioId()));
        entity.setUsuario(usuario);
        entity.setMontoActual(dto.getMontoActual());
        entity.setUltimaActualizacion(dto.getUltimaActualizacion());
        return entity;
    }
}
