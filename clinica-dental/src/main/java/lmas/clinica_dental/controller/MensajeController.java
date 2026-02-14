package lmas.clinica_dental.controller;

import lmas.clinica_dental.entity.Mensaje;
import lmas.clinica_dental.service.IMensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mensajes")
@CrossOrigin(origins = "*")
public class MensajeController {
    
    @Autowired
    private IMensajeService mensajeService;
    
    @GetMapping
    public ResponseEntity<List<Mensaje>> listarTodos() {
        List<Mensaje> mensajes = mensajeService.listarTodos();
        return ResponseEntity.ok(mensajes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Mensaje> buscarPorId(@PathVariable Integer id) {
        Optional<Mensaje> mensaje = mensajeService.buscarPorId(id);
        return mensaje.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Mensaje> crear(@RequestBody Mensaje mensaje) {
        Mensaje nuevoMensaje = mensajeService.guardar(mensaje);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoMensaje);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Mensaje> actualizar(@PathVariable Integer id, @RequestBody Mensaje mensaje) {
        Optional<Mensaje> mensajeExistente = mensajeService.buscarPorId(id);
        if (mensajeExistente.isPresent()) {
            mensaje.setIdMensaje(id);
            Mensaje mensajeActualizado = mensajeService.guardar(mensaje);
            return ResponseEntity.ok(mensajeActualizado);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<Mensaje> mensaje = mensajeService.buscarPorId(id);
        if (mensaje.isPresent()) {
            mensajeService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @PatchMapping("/{id}/marcar-leido")
    public ResponseEntity<Mensaje> marcarComoLeido(@PathVariable Integer id) {
        Optional<Mensaje> mensajeOpt = mensajeService.buscarPorId(id);
        if (mensajeOpt.isPresent()) {
            Mensaje mensaje = mensajeOpt.get();
            mensaje.setLeido(true);
            Mensaje mensajeActualizado = mensajeService.guardar(mensaje);
            return ResponseEntity.ok(mensajeActualizado);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Endpoints específicos
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<Mensaje>> listarPorPaciente(@PathVariable Integer idPaciente) {
        List<Mensaje> mensajes = mensajeService.listarTodos().stream()
                .filter(m -> m.getPaciente().getIdPaciente().equals(idPaciente))
                .toList();
        return ResponseEntity.ok(mensajes);
    }
    
    @GetMapping("/paciente/{idPaciente}/no-leidos")
    public ResponseEntity<List<Mensaje>> listarNoLeidosPorPaciente(@PathVariable Integer idPaciente) {
        List<Mensaje> mensajes = mensajeService.listarTodos().stream()
                .filter(m -> m.getPaciente().getIdPaciente().equals(idPaciente) && !m.getLeido())
                .toList();
        return ResponseEntity.ok(mensajes);
    }
}
