package lmas.clinica_dental.dto;

import java.util.Map;

public class ReportePacientesDTO {
    private Long totalPacientes;
    private Long pacientesActivos;
    private Long pacientesInactivos;
    private Long nuevosPacientes; // En el período especificado
    private Map<String, Long> pacientesPorTipoDocumento;
    private Map<String, Long> pacientesPorEstado;
    
    // Constructores
    public ReportePacientesDTO() {}
    
    public ReportePacientesDTO(Long totalPacientes, Long pacientesActivos, 
                               Long pacientesInactivos, Long nuevosPacientes) {
        this.totalPacientes = totalPacientes;
        this.pacientesActivos = pacientesActivos;
        this.pacientesInactivos = pacientesInactivos;
        this.nuevosPacientes = nuevosPacientes;
    }
    
    // Getters y Setters
    public Long getTotalPacientes() {
        return totalPacientes;
    }
    
    public void setTotalPacientes(Long totalPacientes) {
        this.totalPacientes = totalPacientes;
    }
    
    public Long getPacientesActivos() {
        return pacientesActivos;
    }
    
    public void setPacientesActivos(Long pacientesActivos) {
        this.pacientesActivos = pacientesActivos;
    }
    
    public Long getPacientesInactivos() {
        return pacientesInactivos;
    }
    
    public void setPacientesInactivos(Long pacientesInactivos) {
        this.pacientesInactivos = pacientesInactivos;
    }
    
    public Long getNuevosPacientes() {
        return nuevosPacientes;
    }
    
    public void setNuevosPacientes(Long nuevosPacientes) {
        this.nuevosPacientes = nuevosPacientes;
    }
    
    public Map<String, Long> getPacientesPorTipoDocumento() {
        return pacientesPorTipoDocumento;
    }
    
    public void setPacientesPorTipoDocumento(Map<String, Long> pacientesPorTipoDocumento) {
        this.pacientesPorTipoDocumento = pacientesPorTipoDocumento;
    }
    
    public Map<String, Long> getPacientesPorEstado() {
        return pacientesPorEstado;
    }
    
    public void setPacientesPorEstado(Map<String, Long> pacientesPorEstado) {
        this.pacientesPorEstado = pacientesPorEstado;
    }
}
