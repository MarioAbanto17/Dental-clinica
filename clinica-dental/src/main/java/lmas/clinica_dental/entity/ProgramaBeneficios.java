package lmas.clinica_dental.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "programa_beneficios")
public class ProgramaBeneficios {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_beneficio")
    private Integer idBeneficio;
    
    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;
    
    @Column(name = "puntos_acumulados")
    private Integer puntosAcumulados = 0;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private NivelBeneficio nivel = NivelBeneficio.BRONCE;
    
    // Enum
    public enum NivelBeneficio {
        BRONCE, PLATA, ORO
    }
    
    // Constructores
    public ProgramaBeneficios() {}
    
    // Getters y Setters
    public Integer getIdBeneficio() {
        return idBeneficio;
    }
    
    public void setIdBeneficio(Integer idBeneficio) {
        this.idBeneficio = idBeneficio;
    }
    
    public Paciente getPaciente() {
        return paciente;
    }
    
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    
    public Integer getPuntosAcumulados() {
        return puntosAcumulados;
    }
    
    public void setPuntosAcumulados(Integer puntosAcumulados) {
        this.puntosAcumulados = puntosAcumulados;
    }
    
    public NivelBeneficio getNivel() {
        return nivel;
    }
    
    public void setNivel(NivelBeneficio nivel) {
        this.nivel = nivel;
    }
}
