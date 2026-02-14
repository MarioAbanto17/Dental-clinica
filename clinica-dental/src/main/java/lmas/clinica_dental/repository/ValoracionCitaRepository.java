package lmas.clinica_dental.repository;

import lmas.clinica_dental.entity.ValoracionCita;
import lmas.clinica_dental.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ValoracionCitaRepository extends JpaRepository<ValoracionCita, Integer> {
    
    Optional<ValoracionCita> findByCita(Cita cita);
    
    List<ValoracionCita> findByCalificacionGreaterThanEqual(Byte calificacion);
}
