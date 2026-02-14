package lmas.clinica_dental.repository;

import lmas.clinica_dental.entity.Cita;
import lmas.clinica_dental.entity.Paciente;
import lmas.clinica_dental.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
    
    List<Cita> findByPaciente(Paciente paciente);
    
    List<Cita> findByDoctor(Doctor doctor);
    
    List<Cita> findByEstado(Cita.EstadoCita estado);
    
    List<Cita> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);
    
    List<Cita> findByPacienteAndEstado(Paciente paciente, Cita.EstadoCita estado);
    
    List<Cita> findByPaciente_IdPaciente(Integer idPaciente);
    
    List<Cita> findByDoctor_IdDoctor(Integer idDoctor);
    
    List<Cita> findByDoctor_IdDoctorAndFechaHoraBetween(Integer idDoctor, LocalDateTime inicio, LocalDateTime fin);
}
