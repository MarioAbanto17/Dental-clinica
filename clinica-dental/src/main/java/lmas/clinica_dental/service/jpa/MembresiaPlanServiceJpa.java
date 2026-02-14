package lmas.clinica_dental.service.jpa;

import lmas.clinica_dental.entity.MembresiaPlan;
import lmas.clinica_dental.repository.MembresiaPlanRepository;
import lmas.clinica_dental.service.IMembresiaPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MembresiaPlanServiceJpa implements IMembresiaPlanService {
    
    @Autowired
    private MembresiaPlanRepository membresiaPlanRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<MembresiaPlan> listarTodos() {
        return membresiaPlanRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<MembresiaPlan> buscarPorId(Integer id) {
        return membresiaPlanRepository.findById(id);
    }
    
    @Override
    @Transactional
    public MembresiaPlan guardar(MembresiaPlan membresiaPlan) {
        return membresiaPlanRepository.save(membresiaPlan);
    }
    
    @Override
    @Transactional
    public void eliminar(Integer id) {
        membresiaPlanRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<MembresiaPlan> buscarPorNombre(String nombre) {
        return membresiaPlanRepository.findByNombre(nombre);
    }
}
