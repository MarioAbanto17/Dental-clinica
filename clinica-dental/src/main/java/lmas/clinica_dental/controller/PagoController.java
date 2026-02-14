package lmas.clinica_dental.controller;

import lmas.clinica_dental.entity.Pago;
import lmas.clinica_dental.service.IPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoController {
    
    @Autowired
    private IPagoService pagoService;
    
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
            pago.setEstado(Pago.EstadoPago.PAGADO);
            Pago pagoActualizado = pagoService.guardar(pago);
            return ResponseEntity.ok(pagoActualizado);
        }
        return ResponseEntity.notFound().build();
    }
}
