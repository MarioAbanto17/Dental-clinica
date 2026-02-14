package lmas.clinica_dental.service;

import lmas.clinica_dental.entity.ValoracionCita;
import lmas.clinica_dental.entity.Cita;

import java.util.List;
import java.util.Optional;

public interface IValoracionCitaService {
    
    List<ValoracionCita> listarTodas();
    
    Optional<ValoracionCita> buscarPorId(Integer id);
    
    ValoracionCita guardar(ValoracionCita valoracionCita);
    
    void eliminar(Integer id);
    
    Optional<ValoracionCita> buscarPorCita(Cita cita);
}
