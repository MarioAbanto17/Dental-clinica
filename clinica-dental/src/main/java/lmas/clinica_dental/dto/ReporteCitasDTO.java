package lmas.clinica_dental.dto;

import java.util.Map;

public class ReporteCitasDTO {
    private Long totalCitas;
    private Map<String, Long> citasPorEstado; // PENDIENTE, CONFIRMADA, COMPLETADA, CANCELADA
    private Map<String, Long> citasPorTipo; // PRIMERA_VEZ, CONTROL, EMERGENCIA
    private Map<String, Long> citasPorDoctor;
    private Double tasaCancelacion;
    private Double tasaCompletacion;
    
    // Constructores
    public ReporteCitasDTO() {}
    
    public ReporteCitasDTO(Long totalCitas, Double tasaCancelacion, Double tasaCompletacion) {
        this.totalCitas = totalCitas;
        this.tasaCancelacion = tasaCancelacion;
        this.tasaCompletacion = tasaCompletacion;
    }
    
    // Getters y Setters
    public Long getTotalCitas() {
        return totalCitas;
    }
    
    public void setTotalCitas(Long totalCitas) {
        this.totalCitas = totalCitas;
    }
    
    public Map<String, Long> getCitasPorEstado() {
        return citasPorEstado;
    }
    
    public void setCitasPorEstado(Map<String, Long> citasPorEstado) {
        this.citasPorEstado = citasPorEstado;
    }
    
    public Map<String, Long> getCitasPorTipo() {
        return citasPorTipo;
    }
    
    public void setCitasPorTipo(Map<String, Long> citasPorTipo) {
        this.citasPorTipo = citasPorTipo;
    }
    
    public Map<String, Long> getCitasPorDoctor() {
        return citasPorDoctor;
    }
    
    public void setCitasPorDoctor(Map<String, Long> citasPorDoctor) {
        this.citasPorDoctor = citasPorDoctor;
    }
    
    public Double getTasaCancelacion() {
        return tasaCancelacion;
    }
    
    public void setTasaCancelacion(Double tasaCancelacion) {
        this.tasaCancelacion = tasaCancelacion;
    }
    
    public Double getTasaCompletacion() {
        return tasaCompletacion;
    }
    
    public void setTasaCompletacion(Double tasaCompletacion) {
        this.tasaCompletacion = tasaCompletacion;
    }
}
