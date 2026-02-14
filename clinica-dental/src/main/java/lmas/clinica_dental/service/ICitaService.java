package lmas.clinica_dental.service;

import lmas.clinica_dental.entity.Cita;
import lmas.clinica_dental.entity.Paciente;
import lmas.clinica_dental.entity.Doctor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ICitaService {
    
    List<Cita> listarTodas();
    
    Optional<Cita> buscarPorId(Integer id);
    
    Cita guardar(Cita cita);
    
    void eliminar(Integer id);
    
    List<Cita> buscarPorPaciente(Paciente paciente);
    
    List<Cita> buscarPorDoctor(Doctor doctor);
    
    List<Cita> buscarPorEstado(Cita.EstadoCita estado);
    
    List<Cita> buscarPorRangoFecha(LocalDateTime inicio, LocalDateTime fin);
}
