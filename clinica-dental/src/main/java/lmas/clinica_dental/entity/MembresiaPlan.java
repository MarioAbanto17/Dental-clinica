package lmas.clinica_dental.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "membresias_planes")
public class MembresiaPlan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plan")
    private Integer idPlan;
    
    @Column(nullable = false, length = 50)
    private String nombre;
    
    @Column(name = "precio_mensual", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioMensual;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    // Constructores
    public MembresiaPlan() {}
    
    // Getters y Setters
    public Integer getIdPlan() {
        return idPlan;
    }
    
    public void setIdPlan(Integer idPlan) {
        this.idPlan = idPlan;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public BigDecimal getPrecioMensual() {
        return precioMensual;
    }
    
    public void setPrecioMensual(BigDecimal precioMensual) {
        this.precioMensual = precioMensual;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
