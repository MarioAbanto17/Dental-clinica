package lmas.clinica_dental.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "facturacion_datos")
public class FacturacionDatos {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Integer idFactura;
    
    @ManyToOne
    @JoinColumn(name = "id_pago", nullable = false)
    private Pago pago;
    
    @Column(nullable = false, length = 11)
    private String ruc;
    
    @Column(name = "razon_social", nullable = false, length = 150)
    private String razonSocial;
    
    @Column(name = "direccion_fiscal", length = 255)
    private String direccionFiscal;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_sunat", columnDefinition = "ENUM('PENDIENTE', 'ENVIADO', 'ACEPTADO', 'RECHAZADO')")
    private EstadoSunat estadoSunat = EstadoSunat.PENDIENTE;
    
    @Column(name = "pdf_url", length = 255)
    private String pdfUrl;
    
    // Enum
    public enum EstadoSunat {
        PENDIENTE, ENVIADO, ACEPTADO, RECHAZADO
    }
    
    // Constructores
    public FacturacionDatos() {}
    
    // Getters y Setters
    public Integer getIdFactura() {
        return idFactura;
    }
    
    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }
    
    public Pago getPago() {
        return pago;
    }
    
    public void setPago(Pago pago) {
        this.pago = pago;
    }
    
    public String getRuc() {
        return ruc;
    }
    
    public void setRuc(String ruc) {
        this.ruc = ruc;
    }
    
    public String getRazonSocial() {
        return razonSocial;
    }
    
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
    
    public String getDireccionFiscal() {
        return direccionFiscal;
    }
    
    public void setDireccionFiscal(String direccionFiscal) {
        this.direccionFiscal = direccionFiscal;
    }
    
    public EstadoSunat getEstadoSunat() {
        return estadoSunat;
    }
    
    public void setEstadoSunat(EstadoSunat estadoSunat) {
        this.estadoSunat = estadoSunat;
    }
    
    public String getPdfUrl() {
        return pdfUrl;
    }
    
    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }
}
