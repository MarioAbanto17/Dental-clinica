package lmas.clinica_dental.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "doctores")
public class Doctor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_doctor")
    private Integer idDoctor;
    
    @Column(nullable = false, length = 100)
    private String nombres;
    
    @Column(nullable = false, length = 100)
    private String apellidos;
    
    @Column(nullable = false, length = 100)
    private String especialidad;
    
    @Column(name = "horario_atencion", length = 100)
    private String horarioAtencion;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private EstadoDoctor estado = EstadoDoctor.ACTIVO;
    
    // Enum
    public enum EstadoDoctor {
        ACTIVO, INACTIVO
    }
    
    // Constructores
    public Doctor() {}
    
    // Getters y Setters
    public Integer getIdDoctor() {
        return idDoctor;
    }
    
    public void setIdDoctor(Integer idDoctor) {
        this.idDoctor = idDoctor;
    }
    
    public String getNombres() {
        return nombres;
    }
    
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    
    public String getApellidos() {
        return apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public String getEspecialidad() {
        return especialidad;
    }
    
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
    public String getHorarioAtencion() {
        return horarioAtencion;
    }
    
    public void setHorarioAtencion(String horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
    }
    
    public EstadoDoctor getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoDoctor estado) {
        this.estado = estado;
    }
}
