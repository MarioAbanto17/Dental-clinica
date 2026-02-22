package lmas.clinica_dental.controller;

import lmas.clinica_dental.entity.Cita;
import lmas.clinica_dental.entity.Doctor;
import lmas.clinica_dental.entity.ProgramaBeneficios;
import lmas.clinica_dental.entity.HistorialPuntos;
import lmas.clinica_dental.service.ICitaService;
import lmas.clinica_dental.repository.CitaRepository;
import lmas.clinica_dental.repository.DoctorRepository;
import lmas.clinica_dental.repository.ProgramaBeneficiosRepository;
import lmas.clinica_dental.repository.HistorialPuntosRepository;
import lmas.clinica_dental.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*")
public class CitaController {
    
    @Autowired
    private ICitaService citaService;
    
    @Autowired
    private CitaRepository citaRepository;
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private ProgramaBeneficiosRepository programaBeneficiosRepository;
    
    @Autowired
    private HistorialPuntosRepository historialPuntosRepository;
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @GetMapping
    public ResponseEntity<List<Cita>> listarTodas() {
        List<Cita> citas = citaService.listarTodas();
        return ResponseEntity.ok(citas);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Cita> buscarPorId(@PathVariable Integer id) {
        Optional<Cita> cita = citaService.buscarPorId(id);
        return cita.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Cita> crear(@RequestBody Cita cita) {
        Cita nuevaCita = citaService.guardar(cita);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCita);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Cita> actualizar(@PathVariable Integer id, @RequestBody Cita cita) {
        Optional<Cita> citaExistente = citaService.buscarPorId(id);
        if (citaExistente.isPresent()) {
            cita.setIdCita(id);
            Cita citaActualizada = citaService.guardar(cita);
            return ResponseEntity.ok(citaActualizada);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<Cita> cita = citaService.buscarPorId(id);
        if (cita.isPresent()) {
            citaService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // Obtener disponibilidad de un doctor en una fecha específica
    @GetMapping("/disponibilidad/{idDoctor}/{fecha}")
    public ResponseEntity<List<LocalDateTime>> obtenerDisponibilidad(@PathVariable Integer idDoctor, @PathVariable String fecha) {
        LocalDate localDate = LocalDate.parse(fecha);
        
        // Horarios de trabajo (9:00 AM a 6:00 PM, cada hora)
        List<LocalTime> horariosBase = new ArrayList<>();
        for (int hora = 9; hora <= 17; hora++) {
            horariosBase.add(LocalTime.of(hora, 0));
        }
        
        // Obtener citas existentes del doctor en esa fecha
        LocalDateTime iniciodia = localDate.atStartOfDay();
        LocalDateTime finDia = localDate.atTime(23, 59);
        
        List<Cita> citasExistentes = citaRepository.findByDoctor_IdDoctorAndFechaHoraBetween(
            idDoctor, iniciodia, finDia
        );
        
        // Crear lista de horarios disponibles
        List<LocalDateTime> horariosDisponibles = new ArrayList<>();
        
        for (LocalTime hora : horariosBase) {
            LocalDateTime fechaHora = LocalDateTime.of(localDate, hora);
            
            // Verificar si ya hay una cita en ese horario
            boolean ocupado = citasExistentes.stream()
                .anyMatch(c -> c.getFechaHora().toLocalTime().equals(hora));
            
            if (!ocupado) {
                horariosDisponibles.add(fechaHora);
            }
        }
        
        return ResponseEntity.ok(horariosDisponibles);
    }
    
    // Obtener citas por doctor
    @GetMapping("/doctor/{idDoctor}")
    public ResponseEntity<List<Cita>> listarPorDoctor(@PathVariable Integer idDoctor) {
        List<Cita> citas = citaRepository.findByDoctor_IdDoctor(idDoctor);
        return ResponseEntity.ok(citas);
    }
    
    // Obtener agenda del doctor (próximos 7 días)
    @GetMapping("/doctor/{idDoctor}/agenda")
    public ResponseEntity<List<Map<String, Object>>> obtenerAgenda(@PathVariable Integer idDoctor) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(idDoctor);
        
        if (doctorOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        List<Map<String, Object>> agenda = new ArrayList<>();
        
        // Generar días para los próximos 7 días
        for (int i = 0; i < 7; i++) {
            LocalDate fecha = LocalDate.now().plusDays(i);
            LocalDateTime inicioDia = fecha.atStartOfDay();
            LocalDateTime finDia = fecha.atTime(23, 59);
            
            List<Cita> citasDelDia = citaRepository.findByDoctor_IdDoctorAndFechaHoraBetween(
                idDoctor, inicioDia, finDia
            );
            
            // Horarios base (9 AM - 6 PM)
            List<LocalTime> horariosBase = new ArrayList<>();
            for (int hora = 9; hora <= 17; hora++) {
                horariosBase.add(LocalTime.of(hora, 0));
            }
            
            // Calcular disponibles
            List<LocalDateTime> horariosDisponibles = new ArrayList<>();
            for (LocalTime hora : horariosBase) {
                LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);
                boolean ocupado = citasDelDia.stream()
                    .anyMatch(c -> c.getFechaHora().toLocalTime().equals(hora));
                
                if (!ocupado) {
                    horariosDisponibles.add(fechaHora);
                }
            }
            
            Map<String, Object> dia = new HashMap<>();
            dia.put("fecha", fecha.toString());
            dia.put("horariosDisponibles", horariosDisponibles);
            agenda.add(dia);
        }
        
        return ResponseEntity.ok(agenda);
    }
    
    // Completar cita con verificación de puntualidad
    @PatchMapping("/{id}/completar")
    public ResponseEntity<Map<String, Object>> completarCita(@PathVariable Integer id) {
        Optional<Cita> citaOpt = citaService.buscarPorId(id);
        
        if (citaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Cita cita = citaOpt.get();
        cita.setEstado(Cita.EstadoCita.COMPLETADA);
        citaService.guardar(cita);
        
        // SUMAR PUNTOS BASE por completar la cita
        Integer idPaciente = cita.getPaciente().getIdPaciente();
        int puntosTotales = 10; // Base por cita completada
        String mensajePuntos = "Cita completada (+10 pts)";
        
        // VERIFICAR PUNTUALIDAD: si llegó a tiempo (máx 15 min de retraso)
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime horaCita = cita.getFechaHora();
        Duration diferencia = Duration.between(horaCita, ahora);
        long minutosRetraso = diferencia.toMinutes();
        
        if (minutosRetraso <= 15 && minutosRetraso >= -60) { // Llegó a tiempo o antes
            puntosTotales += 20; // Bonus por puntualidad
            mensajePuntos = "Cita completada (+10 pts) + Puntualidad (+20 pts)";
        }
        
        // Sumar puntos al programa de beneficios
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
        
        beneficio.setPuntosAcumulados(beneficio.getPuntosAcumulados() + puntosTotales);
        
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
        historial.setCantidad(puntosTotales);
        historial.setConcepto(mensajePuntos);
        historialPuntosRepository.save(historial);
        
        Map<String, Object> response = new HashMap<>();
        response.put("cita", cita);
        response.put("puntosGanados", puntosTotales);
        response.put("mensaje", mensajePuntos);
        response.put("beneficio", beneficio);
        
        return ResponseEntity.ok(response);
    }
    
    // Obtener citas de un paciente
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<Cita>> listarPorPaciente(@PathVariable Integer idPaciente) {
        List<Cita> citas = citaRepository.findByPaciente_IdPaciente(idPaciente);
        return ResponseEntity.ok(citas);
    }
}
