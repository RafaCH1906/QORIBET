package org.ide.qoribet.apuesta.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.ide.qoribet.common.enums.apuesta.TipoApuesta;
import org.ide.qoribet.common.enums.apuesta.EstadoApuesta;

public class ApuestaDTO {
    private Long id;
    private Long usuarioId;
    private Long promocionUsuarioId;
    private TipoApuesta tipo;
    private BigDecimal montoApostado;
    private BigDecimal cuotaTotal;
    private BigDecimal gananciaPotencial;
    private BigDecimal gananciaReal;
    private EstadoApuesta estado;
    private LocalDateTime creadaEn;
    private LocalDateTime resueltaEn;
    private List<SeleccionDTO> selecciones;

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

    public Long getPromocionUsuarioId() {
        return promocionUsuarioId;
    }

    public void setPromocionUsuarioId(Long promocionUsuarioId) {
        this.promocionUsuarioId = promocionUsuarioId;
    }

    public TipoApuesta getTipo() {
        return tipo;
    }

    public void setTipo(TipoApuesta tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getMontoApostado() {
        return montoApostado;
    }

    public void setMontoApostado(BigDecimal montoApostado) {
        this.montoApostado = montoApostado;
    }

    public BigDecimal getCuotaTotal() {
        return cuotaTotal;
    }

    public void setCuotaTotal(BigDecimal cuotaTotal) {
        this.cuotaTotal = cuotaTotal;
    }

    public BigDecimal getGananciaPotencial() {
        return gananciaPotencial;
    }

    public void setGananciaPotencial(BigDecimal gananciaPotencial) {
        this.gananciaPotencial = gananciaPotencial;
    }

    public BigDecimal getGananciaReal() {
        return gananciaReal;
    }

    public void setGananciaReal(BigDecimal gananciaReal) {
        this.gananciaReal = gananciaReal;
    }

    public EstadoApuesta getEstado() {
        return estado;
    }

    public void setEstado(EstadoApuesta estado) {
        this.estado = estado;
    }

    public LocalDateTime getCreadaEn() {
        return creadaEn;
    }

    public void setCreadaEn(LocalDateTime creadaEn) {
        this.creadaEn = creadaEn;
    }

    public LocalDateTime getResueltaEn() {
        return resueltaEn;
    }

    public void setResueltaEn(LocalDateTime resueltaEn) {
        this.resueltaEn = resueltaEn;
    }

    public List<SeleccionDTO> getSelecciones() {
        return selecciones;
    }

    public void setSelecciones(List<SeleccionDTO> selecciones) {
        this.selecciones = selecciones;
    }
}