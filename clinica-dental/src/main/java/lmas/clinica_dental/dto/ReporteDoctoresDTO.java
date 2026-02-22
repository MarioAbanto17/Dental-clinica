package lmas.clinica_dental.dto;

import java.util.Map;

public class ReporteDoctoresDTO {
    private Long totalDoctores;
    private Long doctoresActivos;
    private Map<String, Long> doctoresPorEspecialidad;
    private Map<String, Long> citasPorDoctor;
    private Map<String, Double> promedioCalificacionPorDoctor;
    
    // Constructores
    public ReporteDoctoresDTO() {}
    
    public ReporteDoctoresDTO(Long totalDoctores, Long doctoresActivos) {
        this.totalDoctores = totalDoctores;
        this.doctoresActivos = doctoresActivos;
    }
    
    // Getters y Setters
    public Long getTotalDoctores() {
        return totalDoctores;
    }
    
    public void setTotalDoctores(Long totalDoctores) {
        this.totalDoctores = totalDoctores;
    }
    
    public Long getDoctoresActivos() {
        return doctoresActivos;
    }
    
    public void setDoctoresActivos(Long doctoresActivos) {
        this.doctoresActivos = doctoresActivos;
    }
    
    public Map<String, Long> getDoctoresPorEspecialidad() {
        return doctoresPorEspecialidad;
    }
    
    public void setDoctoresPorEspecialidad(Map<String, Long> doctoresPorEspecialidad) {
        this.doctoresPorEspecialidad = doctoresPorEspecialidad;
    }
    
    public Map<String, Long> getCitasPorDoctor() {
        return citasPorDoctor;
    }
    
    public void setCitasPorDoctor(Map<String, Long> citasPorDoctor) {
        this.citasPorDoctor = citasPorDoctor;
    }
    
    public Map<String, Double> getPromedioCalificacionPorDoctor() {
        return promedioCalificacionPorDoctor;
    }
    
    public void setPromedioCalificacionPorDoctor(Map<String, Double> promedioCalificacionPorDoctor) {
        this.promedioCalificacionPorDoctor = promedioCalificacionPorDoctor;
    }
}
