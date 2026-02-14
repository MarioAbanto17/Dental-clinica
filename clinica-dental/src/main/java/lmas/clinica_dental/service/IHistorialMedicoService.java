package lmas.clinica_dental.service;

import lmas.clinica_dental.entity.HistorialMedico;
import lmas.clinica_dental.entity.Paciente;

import java.util.List;
import java.util.Optional;

public interface IHistorialMedicoService {
    
    List<HistorialMedico> listarTodos();
    
    Optional<HistorialMedico> buscarPorId(Integer id);
    
    HistorialMedico guardar(HistorialMedico historialMedico);
    
    void eliminar(Integer id);
    
    Optional<HistorialMedico> buscarPorPaciente(Paciente paciente);
}
