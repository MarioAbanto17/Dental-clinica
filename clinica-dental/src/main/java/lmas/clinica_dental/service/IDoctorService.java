package lmas.clinica_dental.service;

import lmas.clinica_dental.entity.Doctor;

import java.util.List;
import java.util.Optional;

public interface IDoctorService {
    
    List<Doctor> listarTodos();
    
    Optional<Doctor> buscarPorId(Integer id);
    
    Doctor guardar(Doctor doctor);
    
    void eliminar(Integer id);
    
    List<Doctor> buscarPorEspecialidad(String especialidad);
    
    List<Doctor> buscarPorEstado(Doctor.EstadoDoctor estado);
}
