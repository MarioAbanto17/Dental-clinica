package lmas.clinica_dental.service.jpa;

import lmas.clinica_dental.entity.ProgramaBeneficios;
import lmas.clinica_dental.entity.Paciente;
import lmas.clinica_dental.repository.ProgramaBeneficiosRepository;
import lmas.clinica_dental.service.IProgramaBeneficiosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramaBeneficiosServiceJpa implements IProgramaBeneficiosService {
    
    @Autowired
    private ProgramaBeneficiosRepository programaBeneficiosRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<ProgramaBeneficios> listarTodos() {
        return programaBeneficiosRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<ProgramaBeneficios> buscarPorId(Integer id) {
        return programaBeneficiosRepository.findById(id);
    }
    
    @Override
    @Transactional
    public ProgramaBeneficios guardar(ProgramaBeneficios programaBeneficios) {
        return programaBeneficiosRepository.save(programaBeneficios);
    }
    
    @Override
    @Transactional
    public void eliminar(Integer id) {
        programaBeneficiosRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<ProgramaBeneficios> buscarPorPaciente(Paciente paciente) {
        return programaBeneficiosRepository.findByPaciente(paciente);
    }
}
