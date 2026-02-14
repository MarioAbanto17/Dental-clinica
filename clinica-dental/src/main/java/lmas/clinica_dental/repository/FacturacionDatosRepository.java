package lmas.clinica_dental.repository;

import lmas.clinica_dental.entity.FacturacionDatos;
import lmas.clinica_dental.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacturacionDatosRepository extends JpaRepository<FacturacionDatos, Integer> {
    
    Optional<FacturacionDatos> findByPago(Pago pago);
    
    List<FacturacionDatos> findByEstadoSunat(FacturacionDatos.EstadoSunat estado);
    
    List<FacturacionDatos> findByRuc(String ruc);
}
