package lmas.clinica_dental.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "valoraciones_citas")
public class ValoracionCita {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_valoracion")
    private Integer idValoracion;
    
    @ManyToOne
    @JoinColumn(name = "id_cita", nullable = false)
    private Cita cita;
    
    @Column
    private Byte calificacion;
    
    @Column(columnDefinition = "TEXT")
    private String comentario;
    
    @Column(name = "es_anonimo")
    private Boolean esAnonimo = false;
    
    // Constructores
    public ValoracionCita() {}
    
    // Getters y Setters
    public Integer getIdValoracion() {
        return idValoracion;
    }
    
    public void setIdValoracion(Integer idValoracion) {
        this.idValoracion = idValoracion;
    }
    
    public Cita getCita() {
        return cita;
    }
    
    public void setCita(Cita cita) {
        this.cita = cita;
    }
    
    public Byte getCalificacion() {
        return calificacion;
    }
    
    public void setCalificacion(Byte calificacion) {
        this.calificacion = calificacion;
    }
    
    public String getComentario() {
        return comentario;
    }
    
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    public Boolean getEsAnonimo() {
        return esAnonimo;
    }
    
    public void setEsAnonimo(Boolean esAnonimo) {
        this.esAnonimo = esAnonimo;
    }
}
