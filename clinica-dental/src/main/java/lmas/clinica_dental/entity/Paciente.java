package lmas.clinica_dental.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pacientes")
public class Paciente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    private Integer idPaciente;
    
    @Column(nullable = false, length = 100)
    private String nombres;
    
    @Column(nullable = false, length = 100)
    private String apellidos;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", columnDefinition = "ENUM('DNI', 'CE', 'PASAPORTE')")
    private TipoDocumento tipoDocumento = TipoDocumento.DNI;
    
    @Column(name = "numero_documento", unique = true, nullable = false, length = 20)
    private String numeroDocumento;
    
    @Column(unique = true, nullable = false, length = 150)
    private String email;
    
    @Column(length = 20)
    private String telefono;
    
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;
    
    @Column(length = 255)
    private String direccion;
    
    @Column(name = "foto_perfil", length = 255)
    private String fotoPerfil;
    
    @Column(name = "contacto_emergencia_nombre", length = 100)
    private String contactoEmergenciaNombre;
    
    @Column(name = "contacto_emergencia_tel", length = 20)
    private String contactoEmergenciaTel;
    
    @Column(name = "token_recuperacion", length = 100)
    private String tokenRecuperacion;
    
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro = LocalDateTime.now();
    
    @Column(name = "ultimo_acceso")
    private LocalDateTime ultimoAcceso;
    
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ACTIVO', 'BLOQUEADO', 'ELIMINADO')")
    private EstadoPaciente estado = EstadoPaciente.ACTIVO;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('PACIENTE', 'RECEPCION', 'ADMINISTRADOR')")
    private Rol rol = Rol.PACIENTE;
    
    // Enums
    public enum TipoDocumento {
        DNI, CE, PASAPORTE
    }
    
    public enum EstadoPaciente {
        ACTIVO, BLOQUEADO, ELIMINADO
    }
    
    public enum Rol {
        PACIENTE, RECEPCION, ADMINISTRADOR
    }
    
    // Constructores
    public Paciente() {}
    
    // Getters y Setters
    public Integer getIdPaciente() {
        return idPaciente;
    }
    
    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
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
    
    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }
    
    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    
    public String getNumeroDocumento() {
        return numeroDocumento;
    }
    
    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }
    
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String getFotoPerfil() {
        return fotoPerfil;
    }
    
    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
    
    public String getContactoEmergenciaNombre() {
        return contactoEmergenciaNombre;
    }
    
    public void setContactoEmergenciaNombre(String contactoEmergenciaNombre) {
        this.contactoEmergenciaNombre = contactoEmergenciaNombre;
    }
    
    public String getContactoEmergenciaTel() {
        return contactoEmergenciaTel;
    }
    
    public void setContactoEmergenciaTel(String contactoEmergenciaTel) {
        this.contactoEmergenciaTel = contactoEmergenciaTel;
    }
    
    public String getTokenRecuperacion() {
        return tokenRecuperacion;
    }
    
    public void setTokenRecuperacion(String tokenRecuperacion) {
        this.tokenRecuperacion = tokenRecuperacion;
    }
    
    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    public LocalDateTime getUltimoAcceso() {
        return ultimoAcceso;
    }
    
    public void setUltimoAcceso(LocalDateTime ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }
    
    public EstadoPaciente getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoPaciente estado) {
        this.estado = estado;
    }
    
    public Rol getRol() {
        return rol;
    }
    
    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
