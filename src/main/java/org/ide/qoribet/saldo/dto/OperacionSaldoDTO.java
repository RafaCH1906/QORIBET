package org.ide.qoribet.saldo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class OperacionSaldoDTO {
    private Long id;

    private Long saldoDigitalId;

    private String tipo;

    private BigDecimal monto;

    private BigDecimal saldoAnterior;

    private BigDecimal saldoNuevo;

    private String metodoPago;

    private String referencia;

    private String estado;

    private LocalDateTime fechaOperacion;

    private String observaciones;

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSaldoDigitalId(Long saldoDigitalId) {
        this.saldoDigitalId = saldoDigitalId;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public void setSaldoAnterior(BigDecimal saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public void setSaldoNuevo(BigDecimal saldoNuevo) {
        this.saldoNuevo = saldoNuevo;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public void setFechaOperacion(LocalDateTime fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public Long getId() {
        return id;
    }

    public Long getSaldoDigitalId() {
        return saldoDigitalId;
    }

    public String getTipo() {
        return tipo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public BigDecimal getSaldoAnterior() {
        return saldoAnterior;
    }

    public BigDecimal getSaldoNuevo() {
        return saldoNuevo;
    }

    public String getReferencia() {
        return referencia;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDateTime getFechaOperacion() {
        return fechaOperacion;
    }

    public String getObservaciones() {
        return observaciones;
    }
}
