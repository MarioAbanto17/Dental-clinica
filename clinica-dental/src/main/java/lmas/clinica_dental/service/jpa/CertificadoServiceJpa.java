package lmas.clinica_dental.service.jpa;

import lmas.clinica_dental.entity.Certificado;
import lmas.clinica_dental.entity.Paciente;
import lmas.clinica_dental.repository.CertificadoRepository;
import lmas.clinica_dental.service.ICertificadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CertificadoServiceJpa implements ICertificadoService {
    
    @Autowired
    private CertificadoRepository certificadoRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Certificado> listarTodos() {
        return certificadoRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Certificado> buscarPorId(Integer id) {
        return certificadoRepository.findById(id);
    }
    
    @Override
    @Transactional
    public Certificado guardar(Certificado certificado) {
        return certificadoRepository.save(certificado);
    }
    
    @Override
    @Transactional
    public void eliminar(Integer id) {
        certificadoRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Certificado> buscarPorPaciente(Paciente paciente) {
        return certificadoRepository.findByPaciente(paciente);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Certificado> buscarPorEstado(Certificado.EstadoCertificado estado) {
        return certificadoRepository.findByEstado(estado);
    }
}
