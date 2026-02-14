package lmas.clinica_dental.repository;

import lmas.clinica_dental.entity.Notificacion;
import lmas.clinica_dental.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
    
    List<Notificacion> findByPaciente(Paciente paciente);
    
    List<Notificacion> findByPacienteAndLeido(Paciente paciente, Boolean leido);
}
