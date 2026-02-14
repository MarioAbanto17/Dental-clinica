package lmas.clinica_dental.service;

import lmas.clinica_dental.entity.MembresiaPlan;

import java.util.List;
import java.util.Optional;

public interface IMembresiaPlanService {
    
    List<MembresiaPlan> listarTodos();
    
    Optional<MembresiaPlan> buscarPorId(Integer id);
    
    MembresiaPlan guardar(MembresiaPlan membresiaPlan);
    
    void eliminar(Integer id);
    
    Optional<MembresiaPlan> buscarPorNombre(String nombre);
}
