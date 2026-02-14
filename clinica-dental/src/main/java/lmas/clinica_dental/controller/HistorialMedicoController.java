package lmas.clinica_dental.controller;

import lmas.clinica_dental.entity.HistorialMedico;
import lmas.clinica_dental.service.IHistorialMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/historial-medico")
@CrossOrigin(origins = "*")
public class HistorialMedicoController {
    
    @Autowired
    private IHistorialMedicoService historialMedicoService;
    
    @GetMapping
    public ResponseEntity<List<HistorialMedico>> listarTodos() {
        List<HistorialMedico> historiales = historialMedicoService.listarTodos();
        return ResponseEntity.ok(historiales);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<HistorialMedico> buscarPorId(@PathVariable Integer id) {
        Optional<HistorialMedico> historial = historialMedicoService.buscarPorId(id);
        return historial.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<HistorialMedico> crear(@RequestBody HistorialMedico historialMedico) {
        HistorialMedico nuevoHistorial = historialMedicoService.guardar(historialMedico);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoHistorial);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<HistorialMedico> actualizar(@PathVariable Integer id, @RequestBody HistorialMedico historialMedico) {
        Optional<HistorialMedico> historialExistente = historialMedicoService.buscarPorId(id);
        if (historialExistente.isPresent()) {
            historialMedico.setIdHistorial(id);
            HistorialMedico historialActualizado = historialMedicoService.guardar(historialMedico);
            return ResponseEntity.ok(historialActualizado);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<HistorialMedico> historial = historialMedicoService.buscarPorId(id);
        if (historial.isPresent()) {
            historialMedicoService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
