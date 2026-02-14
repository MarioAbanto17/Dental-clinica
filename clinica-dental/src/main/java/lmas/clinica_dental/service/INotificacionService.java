package lmas.clinica_dental.service;

import lmas.clinica_dental.entity.Notificacion;
import lmas.clinica_dental.entity.Paciente;

import java.util.List;
import java.util.Optional;

public interface INotificacionService {
    
    List<Notificacion> listarTodas();
    
    Optional<Notificacion> buscarPorId(Integer id);
    
    Notificacion guardar(Notificacion notificacion);
    
    void eliminar(Integer id);
    
    List<Notificacion> buscarPorPaciente(Paciente paciente);
    
    List<Notificacion> buscarNoLeidas(Paciente paciente);
}
