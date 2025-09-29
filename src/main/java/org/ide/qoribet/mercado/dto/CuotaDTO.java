package org.ide.qoribet.mercado.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CuotaDTO {
    private Integer id;
    private Integer opcionId;
    private BigDecimal valor;
    private LocalDateTime creadaEn;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getOpcionId() { return opcionId; }
    public void setOpcionId(Integer opcionId) { this.opcionId = opcionId; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public LocalDateTime getCreadaEn() { return creadaEn; }
    public void setCreadaEn(LocalDateTime creadaEn) { this.creadaEn = creadaEn; }
}