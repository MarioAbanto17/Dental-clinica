package lmas.clinica_dental.controller;

import lmas.clinica_dental.entity.Paciente;
import lmas.clinica_dental.entity.Cita;
import lmas.clinica_dental.entity.Pago;
import lmas.clinica_dental.entity.ProgramaBeneficios;
import lmas.clinica_dental.service.IPacienteService;
import lmas.clinica_dental.repository.CitaRepository;
import lmas.clinica_dental.repository.PagoRepository;
import lmas.clinica_dental.repository.ProgramaBeneficiosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "*")
public class PacienteController {
    
    @Autowired
    private IPacienteService pacienteService;
    
    @Autowired
    private CitaRepository citaRepository;
    
    @Autowired
    private PagoRepository pagoRepository;
    
    @Autowired
    private ProgramaBeneficiosRepository programaBeneficiosRepository;
    
    @GetMapping
    public ResponseEntity<List<Paciente>> listarTodos() {
        List<Paciente> pacientes = pacienteService.listarTodos();
        return ResponseEntity.ok(pacientes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Integer id) {
        Optional<Paciente> paciente = pacienteService.buscarPorId(id);
        return paciente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Paciente> crear(@RequestBody Paciente paciente) {
        Paciente nuevoPaciente = pacienteService.guardar(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPaciente);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizar(@PathVariable Integer id, @RequestBody Paciente paciente) {
        Optional<Paciente> pacienteExistente = pacienteService.buscarPorId(id);
        if (pacienteExistente.isPresent()) {
            paciente.setIdPaciente(id);
            Paciente pacienteActualizado = pacienteService.guardar(paciente);
            return ResponseEntity.ok(pacienteActualizado);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<Paciente> paciente = pacienteService.buscarPorId(id);
        if (paciente.isPresent()) {
            pacienteService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<Paciente> buscarPorEmail(@PathVariable String email) {
        Optional<Paciente> paciente = pacienteService.buscarPorEmail(email);
        return paciente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/documento/{numeroDocumento}")
    public ResponseEntity<Paciente> buscarPorDocumento(@PathVariable String numeroDocumento) {
        Optional<Paciente> paciente = pacienteService.buscarPorNumeroDocumento(numeroDocumento);
        return paciente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // Perfil completo del paciente con citas, pagos y puntos
    @GetMapping("/{id}/perfil-completo")
    public ResponseEntity<Map<String, Object>> obtenerPerfilCompleto(@PathVariable Integer id) {
        Optional<Paciente> pacienteOpt = pacienteService.buscarPorId(id);
        
        if (pacienteOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Paciente paciente = pacienteOpt.get();
        
        // Obtener citas
        List<Cita> citas = citaRepository.findByPaciente_IdPaciente(id);
        
        // Obtener pagos
        List<Pago> pagos = pagoRepository.findByPaciente_IdPaciente(id);
        
        // Obtener programa de beneficios
        Optional<ProgramaBeneficios> beneficio = programaBeneficiosRepository.findByPaciente_IdPaciente(id);
        
        // Calcular estadísticas
        long totalCitas = citas.size();
        long citasCompletadas = citas.stream().filter(c -> c.getEstado() == Cita.EstadoCita.COMPLETADA).count();
        long citasPendientes = citas.stream().filter(c -> c.getEstado() == Cita.EstadoCita.PENDIENTE).count();
        
        double totalPagado = pagos.stream()
            .filter(p -> p.getEstado() == Pago.EstadoPago.PAGADO)
            .mapToDouble(p -> p.getMonto().doubleValue())
            .sum();
        
        double totalPendiente = pagos.stream()
            .filter(p -> p.getEstado() == Pago.EstadoPago.PENDIENTE)
            .mapToDouble(p -> p.getMonto().doubleValue())
            .sum();
        
        // Construir respuesta
        Map<String, Object> perfil = new HashMap<>();
        perfil.put("paciente", paciente);
        perfil.put("citas", citas);
        perfil.put("pagos", pagos);
        perfil.put("beneficios", beneficio.orElse(null));
        
        Map<String, Object> estadisticas = new HashMap<>();
        estadisticas.put("totalCitas", totalCitas);
        estadisticas.put("citasCompletadas", citasCompletadas);
        estadisticas.put("citasPendientes", citasPendientes);
        estadisticas.put("totalPagado", totalPagado);
        estadisticas.put("totalPendiente", totalPendiente);
        
        perfil.put("estadisticas", estadisticas);
        
        return ResponseEntity.ok(perfil);
    }
}
