package lmas.clinica_dental.service.jpa;

import lmas.clinica_dental.entity.HistorialMedico;
import lmas.clinica_dental.entity.Paciente;
import lmas.clinica_dental.repository.HistorialMedicoRepository;
import lmas.clinica_dental.service.IHistorialMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HistorialMedicoServiceJpa implements IHistorialMedicoService {
    
    @Autowired
    private HistorialMedicoRepository historialMedicoRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<HistorialMedico> listarTodos() {
        return historialMedicoRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<HistorialMedico> buscarPorId(Integer id) {
        return historialMedicoRepository.findById(id);
    }
    
    @Override
    @Transactional
    public HistorialMedico guardar(HistorialMedico historialMedico) {
        return historialMedicoRepository.save(historialMedico);
    }
    
    @Override
    @Transactional
    public void eliminar(Integer id) {
        historialMedicoRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<HistorialMedico> buscarPorPaciente(Paciente paciente) {
        return historialMedicoRepository.findByPaciente(paciente);
    }
}
