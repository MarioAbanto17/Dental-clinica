package lmas.clinica_dental.service;

import lmas.clinica_dental.entity.Paciente;

import java.util.List;
import java.util.Optional;

public interface IPacienteService {
    
    List<Paciente> listarTodos();
    
    Optional<Paciente> buscarPorId(Integer id);
    
    Paciente guardar(Paciente paciente);
    
    void eliminar(Integer id);
    
    Optional<Paciente> buscarPorEmail(String email);
    
    Optional<Paciente> buscarPorNumeroDocumento(String numeroDocumento);
    
    boolean existePorEmail(String email);
    
    boolean existePorNumeroDocumento(String numeroDocumento);
}
