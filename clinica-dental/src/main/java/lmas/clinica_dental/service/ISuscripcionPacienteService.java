package lmas.clinica_dental.service;

import lmas.clinica_dental.entity.SuscripcionPaciente;
import lmas.clinica_dental.entity.Paciente;

import java.util.List;
import java.util.Optional;

public interface ISuscripcionPacienteService {
    
    List<SuscripcionPaciente> listarTodas();
    
    Optional<SuscripcionPaciente> buscarPorId(Integer id);
    
    SuscripcionPaciente guardar(SuscripcionPaciente suscripcionPaciente);
    
    void eliminar(Integer id);
    
    List<SuscripcionPaciente> buscarPorPaciente(Paciente paciente);
    
    List<SuscripcionPaciente> buscarActivas(Paciente paciente);
}
