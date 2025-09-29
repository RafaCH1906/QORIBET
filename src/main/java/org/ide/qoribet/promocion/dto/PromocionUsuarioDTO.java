package org.ide.qoribet.promocion.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PromocionUsuarioDTO {
    private Long id;
    private Long promocionId;
    private Long usuarioId;
    private LocalDateTime fechaAsignacion;
    private Boolean usada;
    private BigDecimal montoAplicado;
    private BigDecimal beneficioOtorgado;
    private LocalDateTime fechaAplicacion;
    private Boolean utilizado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPromocionId() {
        return promocionId;
    }

    public void setPromocionId(Long promocionId) {
        this.promocionId = promocionId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Boolean getUsada() {
        return usada;
    }

    public void setUsada(Boolean usada) {
        this.usada = usada;
    }

    public BigDecimal getMontoAplicado() {
        return montoAplicado;
    }

    public void setMontoAplicado(BigDecimal montoAplicado) {
        this.montoAplicado = montoAplicado;
    }

    public BigDecimal getBeneficioOtorgado() {
        return beneficioOtorgado;
    }

    public void setBeneficioOtorgado(BigDecimal beneficioOtorgado) {
        this.beneficioOtorgado = beneficioOtorgado;
    }

    public LocalDateTime getFechaAplicacion() {
        return fechaAplicacion;
    }

    public void setFechaAplicacion(LocalDateTime fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }

    public Boolean isUtilizado() {
        return utilizado;
    }

    public void setUtilizado(Boolean utilizado) {
        this.utilizado = utilizado;
    }
}
