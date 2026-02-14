package lmas.clinica_dental.controller;

import lmas.clinica_dental.entity.Certificado;
import lmas.clinica_dental.service.ICertificadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/certificados")
@CrossOrigin(origins = "*")
public class CertificadoController {
    
    @Autowired
    private ICertificadoService certificadoService;
    
    @GetMapping
    public ResponseEntity<List<Certificado>> listarTodos() {
        List<Certificado> certificados = certificadoService.listarTodos();
        return ResponseEntity.ok(certificados);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Certificado> buscarPorId(@PathVariable Integer id) {
        Optional<Certificado> certificado = certificadoService.buscarPorId(id);
        return certificado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Certificado> crear(@RequestBody Certificado certificado) {
        Certificado nuevoCertificado = certificadoService.guardar(certificado);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCertificado);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Certificado> actualizar(@PathVariable Integer id, @RequestBody Certificado certificado) {
        Optional<Certificado> certificadoExistente = certificadoService.buscarPorId(id);
        if (certificadoExistente.isPresent()) {
            certificado.setIdCertificado(id);
            Certificado certificadoActualizado = certificadoService.guardar(certificado);
            return ResponseEntity.ok(certificadoActualizado);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<Certificado> certificado = certificadoService.buscarPorId(id);
        if (certificado.isPresent()) {
            certificadoService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @PatchMapping("/{id}/aprobar")
    public ResponseEntity<Certificado> aprobar(@PathVariable Integer id) {
        Optional<Certificado> certificadoOpt = certificadoService.buscarPorId(id);
        if (certificadoOpt.isPresent()) {
            Certificado certificado = certificadoOpt.get();
            certificado.setEstado(Certificado.EstadoCertificado.EMITIDO);
            Certificado certificadoActualizado = certificadoService.guardar(certificado);
            return ResponseEntity.ok(certificadoActualizado);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PatchMapping("/{id}/rechazar")
    public ResponseEntity<Certificado> rechazar(@PathVariable Integer id) {
        Optional<Certificado> certificadoOpt = certificadoService.buscarPorId(id);
        if (certificadoOpt.isPresent()) {
            Certificado certificado = certificadoOpt.get();
            certificado.setEstado(Certificado.EstadoCertificado.RECHAZADO);
            Certificado certificadoActualizado = certificadoService.guardar(certificado);
            return ResponseEntity.ok(certificadoActualizado);
        }
        return ResponseEntity.notFound().build();
    }
}
