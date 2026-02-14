package lmas.clinica_dental.repository;

import lmas.clinica_dental.entity.HistorialPuntos;
import lmas.clinica_dental.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialPuntosRepository extends JpaRepository<HistorialPuntos, Integer> {
    
    List<HistorialPuntos> findByPaciente(Paciente paciente);
    
    List<HistorialPuntos> findByPacienteOrderByFechaDesc(Paciente paciente);
    
    List<HistorialPuntos> findByPaciente_IdPacienteOrderByFechaDesc(Integer idPaciente);
}
