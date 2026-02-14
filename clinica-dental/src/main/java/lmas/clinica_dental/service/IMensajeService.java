package lmas.clinica_dental.service;

import lmas.clinica_dental.entity.Mensaje;
import lmas.clinica_dental.entity.Paciente;

import java.util.List;
import java.util.Optional;

public interface IMensajeService {
    
    List<Mensaje> listarTodos();
    
    Optional<Mensaje> buscarPorId(Integer id);
    
    Mensaje guardar(Mensaje mensaje);
    
    void eliminar(Integer id);
    
    List<Mensaje> buscarPorPaciente(Paciente paciente);
    
    List<Mensaje> buscarMensajesNoLeidos(Paciente paciente);
}
