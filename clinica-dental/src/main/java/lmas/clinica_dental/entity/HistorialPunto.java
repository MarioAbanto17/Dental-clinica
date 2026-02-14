package lmas.clinica_dental.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "historial_puntos")
public class HistorialPunto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaccion")
    private Integer idTransaccion;
    
    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;
    
    @Column(nullable = false)
    private Integer cantidad; // Positivo = ganar, Negativo = canjear
    
    @Column(length = 100)
    private String concepto;
    
    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();
}
