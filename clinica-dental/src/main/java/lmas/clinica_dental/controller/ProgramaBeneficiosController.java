package lmas.clinica_dental.controller;

import lmas.clinica_dental.entity.ProgramaBeneficios;
import lmas.clinica_dental.entity.HistorialPuntos;
import lmas.clinica_dental.service.IProgramaBeneficiosService;
import lmas.clinica_dental.repository.ProgramaBeneficiosRepository;
import lmas.clinica_dental.repository.HistorialPuntosRepository;
import lmas.clinica_dental.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/beneficios")
@CrossOrigin(origins = "*")
public class ProgramaBeneficiosController {
    
    @Autowired
    private IProgramaBeneficiosService programaBeneficiosService;
    
    @Autowired
    private ProgramaBeneficiosRepository programaBeneficiosRepository;
    
    @Autowired
    private HistorialPuntosRepository historialPuntosRepository;
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @GetMapping
    public ResponseEntity<List<ProgramaBeneficios>> listarTodos() {
        List<ProgramaBeneficios> beneficios = programaBeneficiosService.listarTodos();
        return ResponseEntity.ok(beneficios);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProgramaBeneficios> buscarPorId(@PathVariable Integer id) {
        Optional<ProgramaBeneficios> beneficio = programaBeneficiosService.buscarPorId(id);
        return beneficio.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // Obtener puntos de un paciente específico
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<ProgramaBeneficios> obtenerPorPaciente(@PathVariable Integer idPaciente) {
        Optional<ProgramaBeneficios> beneficio = programaBeneficiosRepository.findByPaciente_IdPaciente(idPaciente);
        return beneficio.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // Sumar puntos a un paciente
    @PostMapping("/paciente/{idPaciente}/sumar")
    public ResponseEntity<Map<String, Object>> sumarPuntos(@PathVariable Integer idPaciente, @RequestBody Map<String, Object> request) {
        Integer cantidad = (Integer) request.get("cantidad");
        String concepto = (String) request.get("concepto");
        
        Optional<ProgramaBeneficios> beneficioOpt = programaBeneficiosRepository.findByPaciente_IdPaciente(idPaciente);
        ProgramaBeneficios beneficio;
        
        if (beneficioOpt.isPresent()) {
            beneficio = beneficioOpt.get();
        } else {
            // Crear programa de beneficios si no existe
            beneficio = new ProgramaBeneficios();
            beneficio.setPaciente(pacienteRepository.findById(idPaciente).orElseThrow());
            beneficio.setPuntosAcumulados(0);
            beneficio.setNivel(ProgramaBeneficios.NivelBeneficio.BRONCE);
        }
        
        beneficio.setPuntosAcumulados(beneficio.getPuntosAcumulados() + cantidad);
        
        // Actualizar nivel según puntos
        if (beneficio.getPuntosAcumulados() >= 1000) {
            beneficio.setNivel(ProgramaBeneficios.NivelBeneficio.ORO);
        } else if (beneficio.getPuntosAcumulados() >= 500) {
            beneficio.setNivel(ProgramaBeneficios.NivelBeneficio.PLATA);
        }
        
        programaBeneficiosRepository.save(beneficio);
        
        // Registrar en historial
        HistorialPuntos historial = new HistorialPuntos();
        historial.setPaciente(beneficio.getPaciente());
        historial.setCantidad(cantidad);
        historial.setConcepto(concepto != null ? concepto : "Puntos ganados");
        historialPuntosRepository.save(historial);
        
        Map<String, Object> response = new HashMap<>();
        response.put("beneficio", beneficio);
        response.put("mensaje", "Puntos sumados correctamente");
        
        return ResponseEntity.ok(response);
    }
    
    // Canjear puntos
    @PostMapping("/paciente/{idPaciente}/canjear")
    public ResponseEntity<Map<String, Object>> canjearPuntos(@PathVariable Integer idPaciente, @RequestBody Map<String, Object> request) {
        Integer cantidad = (Integer) request.get("cantidad");
        String concepto = (String) request.get("concepto");
        
        Optional<ProgramaBeneficios> beneficioOpt = programaBeneficiosRepository.findByPaciente_IdPaciente(idPaciente);
        
        if (!beneficioOpt.isPresent()) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "El paciente no tiene programa de beneficios");
            return ResponseEntity.badRequest().body(error);
        }
        
        ProgramaBeneficios beneficio = beneficioOpt.get();
        
        if (beneficio.getPuntosAcumulados() < cantidad) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Puntos insuficientes");
            return ResponseEntity.badRequest().body(error);
        }
        
        beneficio.setPuntosAcumulados(beneficio.getPuntosAcumulados() - cantidad);
        programaBeneficiosRepository.save(beneficio);
        
        // Registrar en historial (negativo)
        HistorialPuntos historial = new HistorialPuntos();
        historial.setPaciente(beneficio.getPaciente());
        historial.setCantidad(-cantidad);
        historial.setConcepto(concepto != null ? concepto : "Canje de puntos");
        historialPuntosRepository.save(historial);
        
        Map<String, Object> response = new HashMap<>();
        response.put("beneficio", beneficio);
        response.put("mensaje", "Puntos canjeados correctamente");
        
        return ResponseEntity.ok(response);
    }
    
    // Obtener historial de puntos de un paciente
    @GetMapping("/paciente/{idPaciente}/historial")
    public ResponseEntity<List<HistorialPuntos>> obtenerHistorial(@PathVariable Integer idPaciente) {
        List<HistorialPuntos> historial = historialPuntosRepository.findByPaciente_IdPacienteOrderByFechaDesc(idPaciente);
        return ResponseEntity.ok(historial);
    }
    
    @PostMapping
    public ResponseEntity<ProgramaBeneficios> crear(@RequestBody ProgramaBeneficios programaBeneficios) {
        ProgramaBeneficios nuevoBeneficio = programaBeneficiosService.guardar(programaBeneficios);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoBeneficio);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProgramaBeneficios> actualizar(@PathVariable Integer id, @RequestBody ProgramaBeneficios programaBeneficios) {
        Optional<ProgramaBeneficios> beneficioExistente = programaBeneficiosService.buscarPorId(id);
        if (beneficioExistente.isPresent()) {
            programaBeneficios.setIdBeneficio(id);
            ProgramaBeneficios beneficioActualizado = programaBeneficiosService.guardar(programaBeneficios);
            return ResponseEntity.ok(beneficioActualizado);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<ProgramaBeneficios> beneficio = programaBeneficiosService.buscarPorId(id);
        if (beneficio.isPresent()) {
            programaBeneficiosService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
