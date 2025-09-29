package org.ide.qoribet.mercado.dto;

public class OpcionDTO {
    private Integer id;
    private String nombre;
    private Integer mercadoId;
    private Boolean activa;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getMercadoId() { return mercadoId; }
    public void setMercadoId(Integer mercadoId) { this.mercadoId = mercadoId; }

    public Boolean getActiva() { return activa; }
    public void setActiva(Boolean activa) { this.activa = activa; }
}