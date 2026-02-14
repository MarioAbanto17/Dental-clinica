package lmas.clinica_dental.service.jpa;

import lmas.clinica_dental.entity.Doctor;
import lmas.clinica_dental.repository.DoctorRepository;
import lmas.clinica_dental.service.IDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceJpa implements IDoctorService {
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Doctor> listarTodos() {
        return doctorRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Doctor> buscarPorId(Integer id) {
        return doctorRepository.findById(id);
    }
    
    @Override
    @Transactional
    public Doctor guardar(Doctor doctor) {
        return doctorRepository.save(doctor);
    }
    
    @Override
    @Transactional
    public void eliminar(Integer id) {
        doctorRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Doctor> buscarPorEspecialidad(String especialidad) {
        return doctorRepository.findByEspecialidad(especialidad);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Doctor> buscarPorEstado(Doctor.EstadoDoctor estado) {
        return doctorRepository.findByEstado(estado);
    }
}
