package lmas.clinica_dental.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "certificados")
public class Certificado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_certificado")
    private Integer idCertificado;
    
    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('ASISTENCIA', 'APTITUD', 'REPOSO')")
    private TipoCertificado tipo;
    
    @Column(length = 255)
    private String motivo;
    
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('SOLICITADO', 'EMITIDO', 'RECHAZADO')")
    private EstadoCertificado estado = EstadoCertificado.SOLICITADO;
    
    @Column(name = "archivo_url", length = 255)
    private String archivoUrl;
    
    @Column(name = "fecha_solicitud")
    private LocalDateTime fechaSolicitud = LocalDateTime.now();
    
    // Enums
    public enum TipoCertificado {
        ASISTENCIA, APTITUD, REPOSO
    }
    
    public enum EstadoCertificado {
        SOLICITADO, EMITIDO, RECHAZADO
    }
    
    // Constructores
    public Certificado() {}
    
    // Getters y Setters
    public Integer getIdCertificado() {
        return idCertificado;
    }
    
    public void setIdCertificado(Integer idCertificado) {
        this.idCertificado = idCertificado;
    }
    
    public Paciente getPaciente() {
        return paciente;
    }
    
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    
    public TipoCertificado getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoCertificado tipo) {
        this.tipo = tipo;
    }
    
    public String getMotivo() {
        return motivo;
    }
    
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    
    public EstadoCertificado getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoCertificado estado) {
        this.estado = estado;
    }
    
    public String getArchivoUrl() {
        return archivoUrl;
    }
    
    public void setArchivoUrl(String archivoUrl) {
        this.archivoUrl = archivoUrl;
    }
    
    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }
    
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
}
