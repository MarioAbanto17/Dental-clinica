package lmas.clinica_dental.service.jpa;

import lmas.clinica_dental.entity.Paciente;
import lmas.clinica_dental.repository.PacienteRepository;
import lmas.clinica_dental.service.IPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceJpa implements IPacienteService {
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> buscarPorId(Integer id) {
        return pacienteRepository.findById(id);
    }
    
    @Override
    @Transactional
    public Paciente guardar(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }
    
    @Override
    @Transactional
    public void eliminar(Integer id) {
        pacienteRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> buscarPorEmail(String email) {
        return pacienteRepository.findByEmail(email);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> buscarPorNumeroDocumento(String numeroDocumento) {
        return pacienteRepository.findByNumeroDocumento(numeroDocumento);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existePorEmail(String email) {
        return pacienteRepository.existsByEmail(email);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existePorNumeroDocumento(String numeroDocumento) {
        return pacienteRepository.existsByNumeroDocumento(numeroDocumento);
    }
}
