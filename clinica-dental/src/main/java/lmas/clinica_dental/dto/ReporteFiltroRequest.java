package lmas.clinica_dental.dto;

import java.time.LocalDate;

public class ReporteFiltroRequest {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String tipoReporte; // FINANCIERO, CITAS, PACIENTES, DOCTORES
    private Long idDoctor;
    private String estado;
    
    // Constructores
    public ReporteFiltroRequest() {}
    
    public ReporteFiltroRequest(LocalDate fechaInicio, LocalDate fechaFin, String tipoReporte) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tipoReporte = tipoReporte;
    }
    
    // Getters y Setters
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public LocalDate getFechaFin() {
        return fechaFin;
    }
    
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public String getTipoReporte() {
        return tipoReporte;
    }
    
    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }
    
    public Long getIdDoctor() {
        return idDoctor;
    }
    
    public void setIdDoctor(Long idDoctor) {
        this.idDoctor = idDoctor;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
