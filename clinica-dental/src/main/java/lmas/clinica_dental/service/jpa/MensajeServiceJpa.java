package lmas.clinica_dental.service.jpa;

import lmas.clinica_dental.entity.Mensaje;
import lmas.clinica_dental.entity.Paciente;
import lmas.clinica_dental.repository.MensajeRepository;
import lmas.clinica_dental.service.IMensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MensajeServiceJpa implements IMensajeService {
    
    @Autowired
    private MensajeRepository mensajeRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Mensaje> listarTodos() {
        return mensajeRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Mensaje> buscarPorId(Integer id) {
        return mensajeRepository.findById(id);
    }
    
    @Override
    @Transactional
    public Mensaje guardar(Mensaje mensaje) {
        return mensajeRepository.save(mensaje);
    }
    
    @Override
    @Transactional
    public void eliminar(Integer id) {
        mensajeRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Mensaje> buscarPorPaciente(Paciente paciente) {
        return mensajeRepository.findByPaciente(paciente);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Mensaje> buscarMensajesNoLeidos(Paciente paciente) {
        return mensajeRepository.findByPacienteAndLeido(paciente, false);
    }
}
