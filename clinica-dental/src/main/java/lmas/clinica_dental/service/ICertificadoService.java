package lmas.clinica_dental.service;

import lmas.clinica_dental.entity.Certificado;
import lmas.clinica_dental.entity.Paciente;

import java.util.List;
import java.util.Optional;

public interface ICertificadoService {
    
    List<Certificado> listarTodos();
    
    Optional<Certificado> buscarPorId(Integer id);
    
    Certificado guardar(Certificado certificado);
    
    void eliminar(Integer id);
    
    List<Certificado> buscarPorPaciente(Paciente paciente);
    
    List<Certificado> buscarPorEstado(Certificado.EstadoCertificado estado);
}
