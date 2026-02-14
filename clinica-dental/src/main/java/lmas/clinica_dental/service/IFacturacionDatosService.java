package lmas.clinica_dental.service;

import lmas.clinica_dental.entity.FacturacionDatos;
import lmas.clinica_dental.entity.Pago;

import java.util.List;
import java.util.Optional;

public interface IFacturacionDatosService {
    
    List<FacturacionDatos> listarTodas();
    
    Optional<FacturacionDatos> buscarPorId(Integer id);
    
    FacturacionDatos guardar(FacturacionDatos facturacionDatos);
    
    void eliminar(Integer id);
    
    Optional<FacturacionDatos> buscarPorPago(Pago pago);
}
