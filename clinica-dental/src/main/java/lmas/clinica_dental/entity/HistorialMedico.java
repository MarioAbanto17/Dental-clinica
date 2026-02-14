package lmas.clinica_dental.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "historial_medico")
public class HistorialMedico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Integer idHistorial;
    
    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;
    
    @Column(name = "grupo_sanguineo", length = 10)
    private String grupoSanguineo;
    
    @Column(columnDefinition = "TEXT")
    private String alergias;
    
    @Column(name = "condiciones_preexistentes", columnDefinition = "TEXT")
    private String condicionesPreexistentes;
    
    @Column(name = "medicamentos_actuales", columnDefinition = "TEXT")
    private String medicamentosActuales;
    
    @Column(name = "antecedentes_familiares", columnDefinition = "TEXT")
    private String antecedentesFamiliares;
    
    @Column(name = "seguro_medico", length = 100)
    private String seguroMedico;
    
    // Constructores
    public HistorialMedico() {}
    
    // Getters y Setters
    public Integer getIdHistorial() {
        return idHistorial;
    }
    
    public void setIdHistorial(Integer idHistorial) {
        this.idHistorial = idHistorial;
    }
    
    public Paciente getPaciente() {
        return paciente;
    }
    
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    
    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }
    
    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }
    
    public String getAlergias() {
        return alergias;
    }
    
    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }
    
    public String getCondicionesPreexistentes() {
        return condicionesPreexistentes;
    }
    
    public void setCondicionesPreexistentes(String condicionesPreexistentes) {
        this.condicionesPreexistentes = condicionesPreexistentes;
    }
    
    public String getMedicamentosActuales() {
        return medicamentosActuales;
    }
    
    public void setMedicamentosActuales(String medicamentosActuales) {
        this.medicamentosActuales = medicamentosActuales;
    }
    
    public String getAntecedentesFamiliares() {
        return antecedentesFamiliares;
    }
    
    public void setAntecedentesFamiliares(String antecedentesFamiliares) {
        this.antecedentesFamiliares = antecedentesFamiliares;
    }
    
    public String getSeguroMedico() {
        return seguroMedico;
    }
    
    public void setSeguroMedico(String seguroMedico) {
        this.seguroMedico = seguroMedico;
    }
}
