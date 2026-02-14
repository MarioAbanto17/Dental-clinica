package lmas.clinica_dental.service.jpa;

import lmas.clinica_dental.entity.Cita;
import lmas.clinica_dental.entity.Paciente;
import lmas.clinica_dental.entity.Doctor;
import lmas.clinica_dental.repository.CitaRepository;
import lmas.clinica_dental.service.ICitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CitaServiceJpa implements ICitaService {
    
    @Autowired
    private CitaRepository citaRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Cita> listarTodas() {
        return citaRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Cita> buscarPorId(Integer id) {
        return citaRepository.findById(id);
    }
    
    @Override
    @Transactional
    public Cita guardar(Cita cita) {
        return citaRepository.save(cita);
    }
    
    @Override
    @Transactional
    public void eliminar(Integer id) {
        citaRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Cita> buscarPorPaciente(Paciente paciente) {
        return citaRepository.findByPaciente(paciente);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Cita> buscarPorDoctor(Doctor doctor) {
        return citaRepository.findByDoctor(doctor);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Cita> buscarPorEstado(Cita.EstadoCita estado) {
        return citaRepository.findByEstado(estado);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Cita> buscarPorRangoFecha(LocalDateTime inicio, LocalDateTime fin) {
        return citaRepository.findByFechaHoraBetween(inicio, fin);
    }
}
