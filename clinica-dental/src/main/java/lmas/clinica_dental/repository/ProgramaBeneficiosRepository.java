package lmas.clinica_dental.repository;

import lmas.clinica_dental.entity.ProgramaBeneficios;
import lmas.clinica_dental.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgramaBeneficiosRepository extends JpaRepository<ProgramaBeneficios, Integer> {
    
    Optional<ProgramaBeneficios> findByPaciente(Paciente paciente);
    
    Optional<ProgramaBeneficios> findByPaciente_IdPaciente(Integer idPaciente);
}
