package lmas.clinica_dental.controller;

import lmas.clinica_dental.entity.Notificacion;
import lmas.clinica_dental.service.INotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notificaciones")
@CrossOrigin(origins = "*")
public class NotificacionController {
    
    @Autowired
    private INotificacionService notificacionService;
    
    @GetMapping
    public ResponseEntity<List<Notificacion>> listarTodas() {
        List<Notificacion> notificaciones = notificacionService.listarTodas();
        return ResponseEntity.ok(notificaciones);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> buscarPorId(@PathVariable Integer id) {
        Optional<Notificacion> notificacion = notificacionService.buscarPorId(id);
        return notificacion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Notificacion> crear(@RequestBody Notificacion notificacion) {
        Notificacion nuevaNotificacion = notificacionService.guardar(notificacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaNotificacion);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Notificacion> actualizar(@PathVariable Integer id, @RequestBody Notificacion notificacion) {
        Optional<Notificacion> notificacionExistente = notificacionService.buscarPorId(id);
        if (notificacionExistente.isPresent()) {
            notificacion.setIdNotificacion(id);
            Notificacion notificacionActualizada = notificacionService.guardar(notificacion);
            return ResponseEntity.ok(notificacionActualizada);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<Notificacion> notificacion = notificacionService.buscarPorId(id);
        if (notificacion.isPresent()) {
            notificacionService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @PatchMapping("/{id}/marcar-leida")
    public ResponseEntity<Notificacion> marcarComoLeida(@PathVariable Integer id) {
        Optional<Notificacion> notificacionOpt = notificacionService.buscarPorId(id);
        if (notificacionOpt.isPresent()) {
            Notificacion notificacion = notificacionOpt.get();
            notificacion.setLeido(true);
            Notificacion notificacionActualizada = notificacionService.guardar(notificacion);
            return ResponseEntity.ok(notificacionActualizada);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Endpoints específicos
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<Notificacion>> listarPorPaciente(@PathVariable Integer idPaciente) {
        List<Notificacion> notificaciones = notificacionService.listarTodas().stream()
                .filter(n -> n.getPaciente().getIdPaciente().equals(idPaciente))
                .toList();
        return ResponseEntity.ok(notificaciones);
    }
    
    @GetMapping("/paciente/{idPaciente}/no-leidas")
    public ResponseEntity<List<Notificacion>> listarNoLeidasPorPaciente(@PathVariable Integer idPaciente) {
        List<Notificacion> notificaciones = notificacionService.listarTodas().stream()
                .filter(n -> n.getPaciente().getIdPaciente().equals(idPaciente) && !n.getLeido())
                .toList();
        return ResponseEntity.ok(notificaciones);
    }
    
    @PatchMapping("/paciente/{idPaciente}/marcar-todas-leidas")
    public ResponseEntity<Void> marcarTodasComoLeidas(@PathVariable Integer idPaciente) {
        List<Notificacion> notificaciones = notificacionService.listarTodas().stream()
                .filter(n -> n.getPaciente().getIdPaciente().equals(idPaciente) && !n.getLeido())
                .toList();
        
        notificaciones.forEach(n -> {
            n.setLeido(true);
            notificacionService.guardar(n);
        });
        
        return ResponseEntity.ok().build();
    }
}
