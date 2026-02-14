package lmas.clinica_dental.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mensajes")
public class Mensaje {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensaje")
    private Integer idMensaje;
    
    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('PACIENTE', 'CLINICA')")
    private Remitente remitente;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenido;
    
    @Column(name = "archivo_adjunto_url", length = 255)
    private String archivoAdjuntoUrl;
    
    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio = LocalDateTime.now();
    
    @Column
    private Boolean leido = false;
    
    // Enum
    public enum Remitente {
        PACIENTE, CLINICA
    }
    
    // Constructores
    public Mensaje() {}
    
    // Getters y Setters
    public Integer getIdMensaje() {
        return idMensaje;
    }
    
    public void setIdMensaje(Integer idMensaje) {
        this.idMensaje = idMensaje;
    }
    
    public Paciente getPaciente() {
        return paciente;
    }
    
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    
    public Remitente getRemitente() {
        return remitente;
    }
    
    public void setRemitente(Remitente remitente) {
        this.remitente = remitente;
    }
    
    public String getContenido() {
        return contenido;
    }
    
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    public String getArchivoAdjuntoUrl() {
        return archivoAdjuntoUrl;
    }
    
    public void setArchivoAdjuntoUrl(String archivoAdjuntoUrl) {
        this.archivoAdjuntoUrl = archivoAdjuntoUrl;
    }
    
    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }
    
    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }
    
    public Boolean getLeido() {
        return leido;
    }
    
    public void setLeido(Boolean leido) {
        this.leido = leido;
    }
}
