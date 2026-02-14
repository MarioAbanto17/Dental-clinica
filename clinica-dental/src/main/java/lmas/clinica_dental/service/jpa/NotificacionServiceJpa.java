package lmas.clinica_dental.service.jpa;

import lmas.clinica_dental.entity.Notificacion;
import lmas.clinica_dental.entity.Paciente;
import lmas.clinica_dental.repository.NotificacionRepository;
import lmas.clinica_dental.service.INotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacionServiceJpa implements INotificacionService {
    
    @Autowired
    private NotificacionRepository notificacionRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Notificacion> listarTodas() {
        return notificacionRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Notificacion> buscarPorId(Integer id) {
        return notificacionRepository.findById(id);
    }
    
    @Override
    @Transactional
    public Notificacion guardar(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }
    
    @Override
    @Transactional
    public void eliminar(Integer id) {
        notificacionRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Notificacion> buscarPorPaciente(Paciente paciente) {
        return notificacionRepository.findByPaciente(paciente);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Notificacion> buscarNoLeidas(Paciente paciente) {
        return notificacionRepository.findByPacienteAndLeido(paciente, false);
    }
}
