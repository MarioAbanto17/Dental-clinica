package lmas.clinica_dental.service.jpa;

import lmas.clinica_dental.entity.FacturacionDatos;
import lmas.clinica_dental.entity.Pago;
import lmas.clinica_dental.repository.FacturacionDatosRepository;
import lmas.clinica_dental.service.IFacturacionDatosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FacturacionDatosServiceJpa implements IFacturacionDatosService {
    
    @Autowired
    private FacturacionDatosRepository facturacionDatosRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<FacturacionDatos> listarTodas() {
        return facturacionDatosRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<FacturacionDatos> buscarPorId(Integer id) {
        return facturacionDatosRepository.findById(id);
    }
    
    @Override
    @Transactional
    public FacturacionDatos guardar(FacturacionDatos facturacionDatos) {
        return facturacionDatosRepository.save(facturacionDatos);
    }
    
    @Override
    @Transactional
    public void eliminar(Integer id) {
        facturacionDatosRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<FacturacionDatos> buscarPorPago(Pago pago) {
        return facturacionDatosRepository.findByPago(pago);
    }
}
