package lmas.clinica_dental.repository;

import lmas.clinica_dental.entity.MembresiaPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembresiaPlanRepository extends JpaRepository<MembresiaPlan, Integer> {
    
    Optional<MembresiaPlan> findByNombre(String nombre);
}
