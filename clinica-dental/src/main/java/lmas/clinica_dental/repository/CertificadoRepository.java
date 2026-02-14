package lmas.clinica_dental.repository;

import lmas.clinica_dental.entity.Certificado;
import lmas.clinica_dental.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificadoRepository extends JpaRepository<Certificado, Integer> {
    
    List<Certificado> findByPaciente(Paciente paciente);
    
    List<Certificado> findByEstado(Certificado.EstadoCertificado estado);
    
    List<Certificado> findByPacienteAndEstado(Paciente paciente, Certificado.EstadoCertificado estado);
}
