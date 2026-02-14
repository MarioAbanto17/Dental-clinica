package lmas.clinica_dental.controller;

import lmas.clinica_dental.entity.SuscripcionPaciente;
import lmas.clinica_dental.service.ISuscripcionPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/suscripciones")
@CrossOrigin(origins = "*")
public class SuscripcionPacienteController {
    
    @Autowired
    private ISuscripcionPacienteService suscripcionPacienteService;
    
    @GetMapping
    public ResponseEntity<List<SuscripcionPaciente>> listarTodas() {
        List<SuscripcionPaciente> suscripciones = suscripcionPacienteService.listarTodas();
        return ResponseEntity.ok(suscripciones);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SuscripcionPaciente> buscarPorId(@PathVariable Integer id) {
        Optional<SuscripcionPaciente> suscripcion = suscripcionPacienteService.buscarPorId(id);
        return suscripcion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<SuscripcionPaciente> crear(@RequestBody SuscripcionPaciente suscripcionPaciente) {
        SuscripcionPaciente nuevaSuscripcion = suscripcionPacienteService.guardar(suscripcionPaciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSuscripcion);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SuscripcionPaciente> actualizar(@PathVariable Integer id, @RequestBody SuscripcionPaciente suscripcionPaciente) {
        Optional<SuscripcionPaciente> suscripcionExistente = suscripcionPacienteService.buscarPorId(id);
        if (suscripcionExistente.isPresent()) {
            suscripcionPaciente.setIdSuscripcion(id);
            SuscripcionPaciente suscripcionActualizada = suscripcionPacienteService.guardar(suscripcionPaciente);
            return ResponseEntity.ok(suscripcionActualizada);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<SuscripcionPaciente> suscripcion = suscripcionPacienteService.buscarPorId(id);
        if (suscripcion.isPresent()) {
            suscripcionPacienteService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<SuscripcionPaciente> cancelar(@PathVariable Integer id) {
        Optional<SuscripcionPaciente> suscripcionOpt = suscripcionPacienteService.buscarPorId(id);
        if (suscripcionOpt.isPresent()) {
            SuscripcionPaciente suscripcion = suscripcionOpt.get();
            suscripcion.setEstado(SuscripcionPaciente.EstadoSuscripcion.CANCELADO);
            SuscripcionPaciente suscripcionActualizada = suscripcionPacienteService.guardar(suscripcion);
            return ResponseEntity.ok(suscripcionActualizada);
        }
        return ResponseEntity.notFound().build();
    }
}
