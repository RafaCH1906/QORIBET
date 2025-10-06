package org.ide.qoribet.promocion.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PromocionDTO {
    private Long id;
    private String codigo;
    private String tipo;
    private BigDecimal valor;
    private BigDecimal montoMinimo;
    private BigDecimal beneficioMaximo;
    private LocalDate fechaInicio;
    private LocalDate fechaExpiracion;
    private Integer usosMaximos;
    private Integer usosActuales;
    private Boolean activo;
    private String descripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getMontoMinimo() {
        return montoMinimo;
    }

    public void setMontoMinimo(BigDecimal montoMinimo) {
        this.montoMinimo = montoMinimo;
    }

    public BigDecimal getBeneficioMaximo() {
        return beneficioMaximo;
    }

    public void setBeneficioMaximo(BigDecimal beneficioMaximo) {
        this.beneficioMaximo = beneficioMaximo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDate fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public Integer getUsosMaximos() {
        return usosMaximos;
    }

    public void setUsosMaximos(Integer usosMaximos) {
        this.usosMaximos = usosMaximos;
    }

    public Integer getUsosActuales() {
        return usosActuales;
    }

    public void setUsosActuales(Integer usosActuales) {
        this.usosActuales = usosActuales;
    }

    public Boolean isActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
