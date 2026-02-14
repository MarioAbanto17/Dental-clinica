package lmas.clinica_dental.service.jpa;

import lmas.clinica_dental.entity.Pago;
import lmas.clinica_dental.entity.Paciente;
import lmas.clinica_dental.repository.PagoRepository;
import lmas.clinica_dental.service.IPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PagoServiceJpa implements IPagoService {
    
    @Autowired
    private PagoRepository pagoRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Pago> listarTodos() {
        return pagoRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Pago> buscarPorId(Integer id) {
        return pagoRepository.findById(id);
    }
    
    @Override
    @Transactional
    public Pago guardar(Pago pago) {
        return pagoRepository.save(pago);
    }
    
    @Override
    @Transactional
    public void eliminar(Integer id) {
        pagoRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Pago> buscarPorPaciente(Paciente paciente) {
        return pagoRepository.findByPaciente(paciente);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Pago> buscarPorEstado(Pago.EstadoPago estado) {
        return pagoRepository.findByEstado(estado);
    }
}
