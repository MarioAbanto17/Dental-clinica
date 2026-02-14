package lmas.clinica_dental.service;

import lmas.clinica_dental.entity.Pago;
import lmas.clinica_dental.entity.Paciente;

import java.util.List;
import java.util.Optional;

public interface IPagoService {
    
    List<Pago> listarTodos();
    
    Optional<Pago> buscarPorId(Integer id);
    
    Pago guardar(Pago pago);
    
    void eliminar(Integer id);
    
    List<Pago> buscarPorPaciente(Paciente paciente);
    
    List<Pago> buscarPorEstado(Pago.EstadoPago estado);
}
