package lmas.clinica_dental.controller;

import lmas.clinica_dental.entity.Pago;
import lmas.clinica_dental.entity.ProgramaBeneficios;
import lmas.clinica_dental.entity.HistorialPuntos;
import lmas.clinica_dental.service.IPagoService;
import lmas.clinica_dental.repository.ProgramaBeneficiosRepository;
import lmas.clinica_dental.repository.HistorialPuntosRepository;
import lmas.clinica_dental.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoController {
    
    @Autowired
    private IPagoService pagoService;
    
    @Autowired
    private ProgramaBeneficiosRepository programaBeneficiosRepository;
    
    @Autowired
    private HistorialPuntosRepository historialPuntosRepository;
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @GetMapping
    public ResponseEntity<List<Pago>> listarTodos() {
        List<Pago> pagos = pagoService.listarTodos();
        return ResponseEntity.ok(pagos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Pago> buscarPorId(@PathVariable Integer id) {
        Optional<Pago> pago = pagoService.buscarPorId(id);
        return pago.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Pago> crear(@RequestBody Pago pago) {
        Pago nuevoPago = pagoService.guardar(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPago);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizar(@PathVariable Integer id, @RequestBody Pago pago) {
        Optional<Pago> pagoExistente = pagoService.buscarPorId(id);
        if (pagoExistente.isPresent()) {
            pago.setIdPago(id);
            Pago pagoActualizado = pagoService.guardar(pago);
            return ResponseEntity.ok(pagoActualizado);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<Pago> pago = pagoService.buscarPorId(id);
        if (pago.isPresent()) {
            pagoService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // Endpoints específicos
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<Pago>> listarPorPaciente(@PathVariable Integer idPaciente) {
        List<Pago> pagos = pagoService.listarTodos().stream()
                .filter(p -> p.getPaciente().getIdPaciente().equals(idPaciente))
                .toList();
        return ResponseEntity.ok(pagos);
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pago>> listarPorEstado(@PathVariable String estado) {
        List<Pago> pagos = pagoService.listarTodos().stream()
                .filter(p -> p.getEstado().name().equals(estado))
                .toList();
        return ResponseEntity.ok(pagos);
    }
    
    @PatchMapping("/{id}/procesar")
    public ResponseEntity<Pago> procesarPago(@PathVariable Integer id) {
        Optional<Pago> pagoOpt = pagoService.buscarPorId(id);
        if (pagoOpt.isPresent()) {
            Pago pago = pagoOpt.get();
            
            // Solo procesar si está pendiente
            if (pago.getEstado() != Pago.EstadoPago.PENDIENTE) {
                return ResponseEntity.badRequest().build();
            }
            
            pago.setEstado(Pago.EstadoPago.PAGADO);
            pago.setFechaPago(LocalDateTime.now());
            Pago pagoActualizado = pagoService.guardar(pago);
            
            // SUMAR PUNTOS AUTOMÁTICAMENTE: 5 puntos por cada 100 soles
            Integer idPaciente = pago.getPaciente().getIdPaciente();
            int montoRounded = pago.getMonto().intValue();
            int puntos = (montoRounded / 100) * 5; // 5 puntos por cada 100 soles
            
            if (puntos > 0) {
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
                
                beneficio.setPuntosAcumulados(beneficio.getPuntosAcumulados() + puntos);
                
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
                historial.setCantidad(puntos);
                historial.setConcepto("Pago realizado - S/" + montoRounded);
                historialPuntosRepository.save(historial);
            }
            
            return ResponseEntity.ok(pagoActualizado);
        }
        return ResponseEntity.notFound().build();
    }
}
