package lmas.clinica_dental.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "suscripciones_pacientes")
public class SuscripcionPaciente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_suscripcion")
    private Integer idSuscripcion;
    
    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;
    
    @ManyToOne
    @JoinColumn(name = "id_plan", nullable = false)
    private MembresiaPlan plan;
    
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;
    
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;
    
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ACTIVO', 'VENCIDO', 'CANCELADO')")
    private EstadoSuscripcion estado = EstadoSuscripcion.ACTIVO;
    
    // Enum
    public enum EstadoSuscripcion {
        ACTIVO, VENCIDO, CANCELADO
    }
    
    // Constructores
    public SuscripcionPaciente() {}
    
    // Getters y Setters
    public Integer getIdSuscripcion() {
        return idSuscripcion;
    }
    
    public void setIdSuscripcion(Integer idSuscripcion) {
        this.idSuscripcion = idSuscripcion;
    }
    
    public Paciente getPaciente() {
        return paciente;
    }
    
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    
    public MembresiaPlan getPlan() {
        return plan;
    }
    
    public void setPlan(MembresiaPlan plan) {
        this.plan = plan;
    }
    
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
    
    public EstadoSuscripcion getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoSuscripcion estado) {
        this.estado = estado;
    }
}
