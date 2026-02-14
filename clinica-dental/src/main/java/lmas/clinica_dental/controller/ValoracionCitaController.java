package lmas.clinica_dental.controller;

import lmas.clinica_dental.entity.ValoracionCita;
import lmas.clinica_dental.service.IValoracionCitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/valoraciones")
@CrossOrigin(origins = "*")
public class ValoracionCitaController {
    
    @Autowired
    private IValoracionCitaService valoracionCitaService;
    
    @GetMapping
    public ResponseEntity<List<ValoracionCita>> listarTodas() {
        List<ValoracionCita> valoraciones = valoracionCitaService.listarTodas();
        return ResponseEntity.ok(valoraciones);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ValoracionCita> buscarPorId(@PathVariable Integer id) {
        Optional<ValoracionCita> valoracion = valoracionCitaService.buscarPorId(id);
        return valoracion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<ValoracionCita> crear(@RequestBody ValoracionCita valoracionCita) {
        ValoracionCita nuevaValoracion = valoracionCitaService.guardar(valoracionCita);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaValoracion);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ValoracionCita> actualizar(@PathVariable Integer id, @RequestBody ValoracionCita valoracionCita) {
        Optional<ValoracionCita> valoracionExistente = valoracionCitaService.buscarPorId(id);
        if (valoracionExistente.isPresent()) {
            valoracionCita.setIdValoracion(id);
            ValoracionCita valoracionActualizada = valoracionCitaService.guardar(valoracionCita);
            return ResponseEntity.ok(valoracionActualizada);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<ValoracionCita> valoracion = valoracionCitaService.buscarPorId(id);
        if (valoracion.isPresent()) {
            valoracionCitaService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
