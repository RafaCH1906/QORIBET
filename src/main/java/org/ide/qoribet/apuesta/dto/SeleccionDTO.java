package org.ide.qoribet.apuesta.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SeleccionDTO {
    private Long id;
    private Long apuestaId;
    private Long opcionId;
    private Long partidoId;
    private BigDecimal cuotaTomada;
    private String resultado;
    private LocalDateTime fechaResultado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApuestaId() {
        return apuestaId;
    }

    public void setApuestaId(Long apuestaId) {
        this.apuestaId = apuestaId;
    }

    public Long getOpcionId() {
        return opcionId;
    }

    public void setOpcionId(Long opcionId) {
        this.opcionId = opcionId;
    }

    public Long getPartidoId() {
        return partidoId;
    }

    public void setPartidoId(Long partidoId) {
        this.partidoId = partidoId;
    }

    public BigDecimal getCuotaTomada() {
        return cuotaTomada;
    }

    public void setCuotaTomada(BigDecimal cuotaTomada) {
        this.cuotaTomada = cuotaTomada;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public LocalDateTime getFechaResultado() {
        return fechaResultado;
    }

    public void setFechaResultado(LocalDateTime fechaResultado) {
        this.fechaResultado = fechaResultado;
    }
}