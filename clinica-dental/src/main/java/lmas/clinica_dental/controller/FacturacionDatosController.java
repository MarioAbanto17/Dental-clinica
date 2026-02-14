package lmas.clinica_dental.controller;

import lmas.clinica_dental.entity.FacturacionDatos;
import lmas.clinica_dental.service.IFacturacionDatosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/facturacion")
@CrossOrigin(origins = "*")
public class FacturacionDatosController {
    
    @Autowired
    private IFacturacionDatosService facturacionDatosService;
    
    @GetMapping
    public ResponseEntity<List<FacturacionDatos>> listarTodas() {
        List<FacturacionDatos> facturas = facturacionDatosService.listarTodas();
        return ResponseEntity.ok(facturas);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<FacturacionDatos> buscarPorId(@PathVariable Integer id) {
        Optional<FacturacionDatos> factura = facturacionDatosService.buscarPorId(id);
        return factura.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<FacturacionDatos> crear(@RequestBody FacturacionDatos facturacionDatos) {
        FacturacionDatos nuevaFactura = facturacionDatosService.guardar(facturacionDatos);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaFactura);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<FacturacionDatos> actualizar(@PathVariable Integer id, @RequestBody FacturacionDatos facturacionDatos) {
        Optional<FacturacionDatos> facturaExistente = facturacionDatosService.buscarPorId(id);
        if (facturaExistente.isPresent()) {
            facturacionDatos.setIdFactura(id);
            FacturacionDatos facturaActualizada = facturacionDatosService.guardar(facturacionDatos);
            return ResponseEntity.ok(facturaActualizada);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<FacturacionDatos> factura = facturacionDatosService.buscarPorId(id);
        if (factura.isPresent()) {
            facturacionDatosService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
