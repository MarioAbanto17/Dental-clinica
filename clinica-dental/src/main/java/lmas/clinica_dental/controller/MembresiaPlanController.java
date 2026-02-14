package lmas.clinica_dental.controller;

import lmas.clinica_dental.entity.MembresiaPlan;
import lmas.clinica_dental.service.IMembresiaPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/planes")
@CrossOrigin(origins = "*")
public class MembresiaPlanController {
    
    @Autowired
    private IMembresiaPlanService membresiaPlanService;
    
    @GetMapping
    public ResponseEntity<List<MembresiaPlan>> listarTodos() {
        List<MembresiaPlan> planes = membresiaPlanService.listarTodos();
        return ResponseEntity.ok(planes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MembresiaPlan> buscarPorId(@PathVariable Integer id) {
        Optional<MembresiaPlan> plan = membresiaPlanService.buscarPorId(id);
        return plan.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<MembresiaPlan> crear(@RequestBody MembresiaPlan membresiaPlan) {
        MembresiaPlan nuevoPlan = membresiaPlanService.guardar(membresiaPlan);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPlan);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MembresiaPlan> actualizar(@PathVariable Integer id, @RequestBody MembresiaPlan membresiaPlan) {
        Optional<MembresiaPlan> planExistente = membresiaPlanService.buscarPorId(id);
        if (planExistente.isPresent()) {
            membresiaPlan.setIdPlan(id);
            MembresiaPlan planActualizado = membresiaPlanService.guardar(membresiaPlan);
            return ResponseEntity.ok(planActualizado);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<MembresiaPlan> plan = membresiaPlanService.buscarPorId(id);
        if (plan.isPresent()) {
            membresiaPlanService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
