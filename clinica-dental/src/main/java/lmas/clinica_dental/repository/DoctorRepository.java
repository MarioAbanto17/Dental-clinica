package lmas.clinica_dental.repository;

import lmas.clinica_dental.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    
    List<Doctor> findByEspecialidad(String especialidad);
    
    List<Doctor> findByEstado(Doctor.EstadoDoctor estado);
}
