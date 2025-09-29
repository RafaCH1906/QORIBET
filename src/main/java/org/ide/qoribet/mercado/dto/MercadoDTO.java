package org.ide.qoribet.mercado.dto;

import org.ide.qoribet.common.enums.mercado.TipoMercado;

public class MercadoDTO {

    private Long id;
    private Long partidoId;
    private TipoMercado tipo;
    private String descripcion;
    private boolean activo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPartidoId() { return partidoId; }
    public void setPartidoId(Long partidoId) { this.partidoId = partidoId; }

    public TipoMercado getTipo() { return tipo; }
    public void setTipo(TipoMercado tipo) { this.tipo = tipo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
