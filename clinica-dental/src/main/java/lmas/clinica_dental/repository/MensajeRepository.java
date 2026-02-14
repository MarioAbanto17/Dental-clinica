package lmas.clinica_dental.repository;

import lmas.clinica_dental.entity.Mensaje;
import lmas.clinica_dental.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Integer> {
    
    List<Mensaje> findByPaciente(Paciente paciente);
    
    List<Mensaje> findByPacienteAndLeido(Paciente paciente, Boolean leido);
}
