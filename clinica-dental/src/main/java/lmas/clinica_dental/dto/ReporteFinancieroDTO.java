package lmas.clinica_dental.dto;

import java.math.BigDecimal;
import java.util.Map;

public class ReporteFinancieroDTO {
    private BigDecimal totalIngresos;
    private BigDecimal totalPendiente;
    private BigDecimal totalPagado;
    private Integer cantidadPagos;
    private Map<String, Long> pagosPorMetodo; // TARJETA, YAPE, PLIN, EFECTIVO
    private Map<String, BigDecimal> ingresosPorMetodo;
    
    // Constructores
    public ReporteFinancieroDTO() {}
    
    public ReporteFinancieroDTO(BigDecimal totalIngresos, BigDecimal totalPendiente, 
                                BigDecimal totalPagado, Integer cantidadPagos) {
        this.totalIngresos = totalIngresos;
        this.totalPendiente = totalPendiente;
        this.totalPagado = totalPagado;
        this.cantidadPagos = cantidadPagos;
    }
    
    // Getters y Setters
    public BigDecimal getTotalIngresos() {
        return totalIngresos;
    }
    
    public void setTotalIngresos(BigDecimal totalIngresos) {
        this.totalIngresos = totalIngresos;
    }
    
    public BigDecimal getTotalPendiente() {
        return totalPendiente;
    }
    
    public void setTotalPendiente(BigDecimal totalPendiente) {
        this.totalPendiente = totalPendiente;
    }
    
    public BigDecimal getTotalPagado() {
        return totalPagado;
    }
    
    public void setTotalPagado(BigDecimal totalPagado) {
        this.totalPagado = totalPagado;
    }
    
    public Integer getCantidadPagos() {
        return cantidadPagos;
    }
    
    public void setCantidadPagos(Integer cantidadPagos) {
        this.cantidadPagos = cantidadPagos;
    }
    
    public Map<String, Long> getPagosPorMetodo() {
        return pagosPorMetodo;
    }
    
    public void setPagosPorMetodo(Map<String, Long> pagosPorMetodo) {
        this.pagosPorMetodo = pagosPorMetodo;
    }
    
    public Map<String, BigDecimal> getIngresosPorMetodo() {
        return ingresosPorMetodo;
    }
    
    public void setIngresosPorMetodo(Map<String, BigDecimal> ingresosPorMetodo) {
        this.ingresosPorMetodo = ingresosPorMetodo;
    }
}
