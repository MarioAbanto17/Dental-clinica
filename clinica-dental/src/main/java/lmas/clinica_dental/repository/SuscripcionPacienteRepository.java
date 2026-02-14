package lmas.clinica_dental.repository;

import lmas.clinica_dental.entity.SuscripcionPaciente;
import lmas.clinica_dental.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuscripcionPacienteRepository extends JpaRepository<SuscripcionPaciente, Integer> {
    
    List<SuscripcionPaciente> findByPaciente(Paciente paciente);
    
    List<SuscripcionPaciente> findByPacienteAndEstado(Paciente paciente, SuscripcionPaciente.EstadoSuscripcion estado);
    
    List<SuscripcionPaciente> findByEstado(SuscripcionPaciente.EstadoSuscripcion estado);
}
