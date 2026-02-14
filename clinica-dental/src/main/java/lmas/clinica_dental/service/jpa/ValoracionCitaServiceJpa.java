package lmas.clinica_dental.service.jpa;

import lmas.clinica_dental.entity.ValoracionCita;
import lmas.clinica_dental.entity.Cita;
import lmas.clinica_dental.repository.ValoracionCitaRepository;
import lmas.clinica_dental.service.IValoracionCitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ValoracionCitaServiceJpa implements IValoracionCitaService {
    
    @Autowired
    private ValoracionCitaRepository valoracionCitaRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<ValoracionCita> listarTodas() {
        return valoracionCitaRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<ValoracionCita> buscarPorId(Integer id) {
        return valoracionCitaRepository.findById(id);
    }
    
    @Override
    @Transactional
    public ValoracionCita guardar(ValoracionCita valoracionCita) {
        return valoracionCitaRepository.save(valoracionCita);
    }
    
    @Override
    @Transactional
    public void eliminar(Integer id) {
        valoracionCitaRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<ValoracionCita> buscarPorCita(Cita cita) {
        return valoracionCitaRepository.findByCita(cita);
    }
}
