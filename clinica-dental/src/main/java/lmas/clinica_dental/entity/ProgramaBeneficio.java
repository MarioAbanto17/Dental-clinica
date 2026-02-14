package lmas.clinica_dental.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "programa_beneficios")
public class ProgramaBeneficio {
    
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
    @Column(nullable = false)
    private Nivel nivel = Nivel.BRONCE;
    
    public enum Nivel {
        BRONCE,
        PLATA,
        ORO
    }
}
