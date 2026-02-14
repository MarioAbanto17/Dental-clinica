package lmas.clinica_dental.controller;

import lmas.clinica_dental.entity.Doctor;
import lmas.clinica_dental.service.IDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/doctores")
@CrossOrigin(origins = "*")
public class DoctorController {
    
    @Autowired
    private IDoctorService doctorService;
    
    @GetMapping
    public ResponseEntity<List<Doctor>> listarTodos() {
        List<Doctor> doctores = doctorService.listarTodos();
        return ResponseEntity.ok(doctores);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> buscarPorId(@PathVariable Integer id) {
        Optional<Doctor> doctor = doctorService.buscarPorId(id);
        return doctor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Doctor> crear(@RequestBody Doctor doctor) {
        Doctor nuevoDoctor = doctorService.guardar(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDoctor);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Doctor> actualizar(@PathVariable Integer id, @RequestBody Doctor doctor) {
        Optional<Doctor> doctorExistente = doctorService.buscarPorId(id);
        if (doctorExistente.isPresent()) {
            doctor.setIdDoctor(id);
            Doctor doctorActualizado = doctorService.guardar(doctor);
            return ResponseEntity.ok(doctorActualizado);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<Doctor> doctor = doctorService.buscarPorId(id);
        if (doctor.isPresent()) {
            doctorService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/especialidad/{especialidad}")
    public ResponseEntity<List<Doctor>> buscarPorEspecialidad(@PathVariable String especialidad) {
        List<Doctor> doctores = doctorService.buscarPorEspecialidad(especialidad);
        return ResponseEntity.ok(doctores);
    }
}
