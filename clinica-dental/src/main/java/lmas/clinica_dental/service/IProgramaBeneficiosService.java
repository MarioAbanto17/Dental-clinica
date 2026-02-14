package lmas.clinica_dental.service;

import lmas.clinica_dental.entity.ProgramaBeneficios;
import lmas.clinica_dental.entity.Paciente;

import java.util.List;
import java.util.Optional;

public interface IProgramaBeneficiosService {
    
    List<ProgramaBeneficios> listarTodos();
    
    Optional<ProgramaBeneficios> buscarPorId(Integer id);
    
    ProgramaBeneficios guardar(ProgramaBeneficios programaBeneficios);
    
    void eliminar(Integer id);
    
    Optional<ProgramaBeneficios> buscarPorPaciente(Paciente paciente);
}
