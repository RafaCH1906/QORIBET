package org.ide.qoribet.saldo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SaldoDigitalDTO {
    private Long id;
    private Long usuarioId;
    private BigDecimal montoActual;
    private LocalDateTime ultimaActualizacion;

    // Constructores
    public SaldoDigitalDTO() {}

    public SaldoDigitalDTO(Long id, Long usuarioId, BigDecimal montoActual, LocalDateTime ultimaActualizacion) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.montoActual = montoActual;
        this.ultimaActualizacion = ultimaActualizacion;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public BigDecimal getMontoActual() {
        return montoActual;
    }

    public void setMontoActual(BigDecimal montoActual) {
        this.montoActual = montoActual;
    }

    public LocalDateTime getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public void setUltimaActualizacion(LocalDateTime ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

    @Override
    public String toString() {
        return "SaldoDigitalDTO{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", montoActual=" + montoActual +
                ", ultimaActualizacion=" + ultimaActualizacion +
                '}';
    }
}