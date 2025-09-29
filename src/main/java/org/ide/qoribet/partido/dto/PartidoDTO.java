package org.ide.qoribet.partido.dto;

import java.time.LocalDateTime;

public class PartidoDTO {
    private Long id;
    private LocalDateTime fecha;
    private Long equipoLocalId;
    private Long equipoVisitanteId;
    private Long ligaId;
    private Integer golesLocal;
    private Integer golesVisitante;
    private String estado;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public Long getEquipoLocalId() { return equipoLocalId; }
    public void setEquipoLocalId(Long equipoLocalId) { this.equipoLocalId = equipoLocalId; }

    public Long getEquipoVisitanteId() { return equipoVisitanteId; }
    public void setEquipoVisitanteId(Long equipoVisitanteId) { this.equipoVisitanteId = equipoVisitanteId; }

    public Long getLigaId() { return ligaId; }
    public void setLigaId(Long ligaId) { this.ligaId = ligaId; }

    public Integer getGolesLocal() { return golesLocal; }
    public void setGolesLocal(Integer golesLocal) { this.golesLocal = golesLocal; }

    public Integer getGolesVisitante() { return golesVisitante; }
    public void setGolesVisitante(Integer golesVisitante) { this.golesVisitante = golesVisitante; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}