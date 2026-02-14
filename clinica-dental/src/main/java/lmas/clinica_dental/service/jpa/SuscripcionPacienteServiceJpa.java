package lmas.clinica_dental.service.jpa;

import lmas.clinica_dental.entity.SuscripcionPaciente;
import lmas.clinica_dental.entity.Paciente;
import lmas.clinica_dental.repository.SuscripcionPacienteRepository;
import lmas.clinica_dental.service.ISuscripcionPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SuscripcionPacienteServiceJpa implements ISuscripcionPacienteService {
    
    @Autowired
    private SuscripcionPacienteRepository suscripcionPacienteRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<SuscripcionPaciente> listarTodas() {
        return suscripcionPacienteRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<SuscripcionPaciente> buscarPorId(Integer id) {
        return suscripcionPacienteRepository.findById(id);
    }
    
    @Override
    @Transactional
    public SuscripcionPaciente guardar(SuscripcionPaciente suscripcionPaciente) {
        return suscripcionPacienteRepository.save(suscripcionPaciente);
    }
    
    @Override
    @Transactional
    public void eliminar(Integer id) {
        suscripcionPacienteRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<SuscripcionPaciente> buscarPorPaciente(Paciente paciente) {
        return suscripcionPacienteRepository.findByPaciente(paciente);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<SuscripcionPaciente> buscarActivas(Paciente paciente) {
        return suscripcionPacienteRepository.findByPacienteAndEstado(paciente, SuscripcionPaciente.EstadoSuscripcion.ACTIVO);
    }
}
