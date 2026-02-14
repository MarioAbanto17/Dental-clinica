package lmas.clinica_dental.repository;

import lmas.clinica_dental.entity.Pago;
import lmas.clinica_dental.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    
    List<Pago> findByPaciente(Paciente paciente);
    
    List<Pago> findByPaciente_IdPaciente(Integer idPaciente);
    
    List<Pago> findByEstado(Pago.EstadoPago estado);
}
