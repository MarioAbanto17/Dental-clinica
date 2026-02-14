package lmas.clinica_dental.repository;

import lmas.clinica_dental.entity.HistorialMedico;
import lmas.clinica_dental.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistorialMedicoRepository extends JpaRepository<HistorialMedico, Integer> {
    
    Optional<HistorialMedico> findByPaciente(Paciente paciente);
}
