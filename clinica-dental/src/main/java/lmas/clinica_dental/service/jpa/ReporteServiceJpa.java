package lmas.clinica_dental.service.jpa;

import lmas.clinica_dental.dto.*;
import lmas.clinica_dental.entity.*;
import lmas.clinica_dental.repository.*;
import lmas.clinica_dental.service.IReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReporteServiceJpa implements IReporteService {
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Autowired
    private CitaRepository citaRepository;
    
    @Autowired
    private PagoRepository pagoRepository;
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private ValoracionCitaRepository valoracionCitaRepository;
    
    @Override
    @Transactional(readOnly = true)
    public ReporteFinancieroDTO generarReporteFinanciero(LocalDate fechaInicio, LocalDate fechaFin) {
        ReporteFinancieroDTO reporte = new ReporteFinancieroDTO();
        
        // Convertir LocalDate a LocalDateTime para consultas
        LocalDateTime inicio = fechaInicio != null ? fechaInicio.atStartOfDay() : LocalDateTime.now().minusMonths(1);
        LocalDateTime fin = fechaFin != null ? fechaFin.atTime(LocalTime.MAX) : LocalDateTime.now();
        
        // Obtener todos los pagos en el rango de fechas
        List<Pago> pagos = pagoRepository.findAll().stream()
            .filter(p -> p.getFechaPago() != null && 
                        !p.getFechaPago().isBefore(inicio) && 
                        !p.getFechaPago().isAfter(fin))
            .collect(Collectors.toList());
        
        // Calcular totales
        BigDecimal totalIngresos = pagos.stream()
            .map(Pago::getMonto)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalPagado = pagos.stream()
            .filter(p -> "PAGADO".equals(p.getEstado()))
            .map(Pago::getMonto)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalPendiente = pagos.stream()
            .filter(p -> "PENDIENTE".equals(p.getEstado()))
            .map(Pago::getMonto)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        reporte.setTotalIngresos(totalIngresos);
        reporte.setTotalPagado(totalPagado);
        reporte.setTotalPendiente(totalPendiente);
        reporte.setCantidadPagos(pagos.size());
        
        // Agrupar por método de pago - cantidad (convertir ENUM a String)
        Map<String, Long> pagosPorMetodo = pagos.stream()
            .collect(Collectors.groupingBy(
                p -> p.getMetodoPago() != null ? p.getMetodoPago().toString() : "DESCONOCIDO",
                Collectors.counting()
            ));
        reporte.setPagosPorMetodo(pagosPorMetodo);
        
        // Agrupar por método de pago - ingresos (convertir ENUM a String)
        Map<String, BigDecimal> ingresosPorMetodo = pagos.stream()
            .collect(Collectors.groupingBy(
                p -> p.getMetodoPago() != null ? p.getMetodoPago().toString() : "DESCONOCIDO",
                Collectors.reducing(BigDecimal.ZERO, Pago::getMonto, BigDecimal::add)
            ));
        reporte.setIngresosPorMetodo(ingresosPorMetodo);
        
        return reporte;
    }
    
    @Override
    @Transactional(readOnly = true)
    public ReporteCitasDTO generarReporteCitas(LocalDate fechaInicio, LocalDate fechaFin) {
        ReporteCitasDTO reporte = new ReporteCitasDTO();
        
        // Convertir LocalDate a LocalDateTime
        LocalDateTime inicio = fechaInicio != null ? fechaInicio.atStartOfDay() : LocalDateTime.now().minusMonths(1);
        LocalDateTime fin = fechaFin != null ? fechaFin.atTime(LocalTime.MAX) : LocalDateTime.now();
        
        // Obtener todas las citas en el rango de fechas
        List<Cita> citas = citaRepository.findAll().stream()
            .filter(c -> c.getFechaHora() != null && 
                        !c.getFechaHora().isBefore(inicio) && 
                        !c.getFechaHora().isAfter(fin))
            .collect(Collectors.toList());
        
        reporte.setTotalCitas((long) citas.size());
        
        // Agrupar por estado (convertir ENUM a String)
        Map<String, Long> citasPorEstado = citas.stream()
            .collect(Collectors.groupingBy(
                c -> c.getEstado() != null ? c.getEstado().toString() : "DESCONOCIDO",
                Collectors.counting()
            ));
        reporte.setCitasPorEstado(citasPorEstado);
        
        // Agrupar por tipo de consulta (convertir ENUM a String)
        Map<String, Long> citasPorTipo = citas.stream()
            .collect(Collectors.groupingBy(
                c -> c.getTipoConsulta() != null ? c.getTipoConsulta().toString() : "DESCONOCIDO",
                Collectors.counting()
            ));
        reporte.setCitasPorTipo(citasPorTipo);
        
        // Agrupar por doctor (nombre completo)
        Map<String, Long> citasPorDoctor = citas.stream()
            .filter(c -> c.getDoctor() != null)
            .collect(Collectors.groupingBy(
                c -> c.getDoctor().getNombres() + " " + c.getDoctor().getApellidos(),
                Collectors.counting()
            ));
        reporte.setCitasPorDoctor(citasPorDoctor);
        
        // Calcular tasas
        long totalCitas = citas.size();
        if (totalCitas > 0) {
            long canceladas = citasPorEstado.getOrDefault("CANCELADA", 0L);
            long completadas = citasPorEstado.getOrDefault("COMPLETADA", 0L);
            
            reporte.setTasaCancelacion((canceladas * 100.0) / totalCitas);
            reporte.setTasaCompletacion((completadas * 100.0) / totalCitas);
        } else {
            reporte.setTasaCancelacion(0.0);
            reporte.setTasaCompletacion(0.0);
        }
        
        return reporte;
    }
    
    @Override
    @Transactional(readOnly = true)
    public ReportePacientesDTO generarReportePacientes(LocalDate fechaInicio, LocalDate fechaFin) {
        ReportePacientesDTO reporte = new ReportePacientesDTO();
        
        // Convertir LocalDate a LocalDateTime
        LocalDateTime inicio = fechaInicio != null ? fechaInicio.atStartOfDay() : LocalDateTime.now().minusMonths(1);
        LocalDateTime fin = fechaFin != null ? fechaFin.atTime(LocalTime.MAX) : LocalDateTime.now();
        
        // Obtener todos los pacientes
        List<Paciente> todosPacientes = pacienteRepository.findAll();
        
        reporte.setTotalPacientes((long) todosPacientes.size());
        
        // Contar activos e inactivos
        long activos = todosPacientes.stream()
            .filter(p -> "ACTIVO".equals(p.getEstado()))
            .count();
        long inactivos = todosPacientes.size() - activos;
        
        reporte.setPacientesActivos(activos);
        reporte.setPacientesInactivos(inactivos);
        
        // Nuevos pacientes en el período
        long nuevosPacientes = todosPacientes.stream()
            .filter(p -> p.getFechaRegistro() != null &&
                        !p.getFechaRegistro().isBefore(inicio) &&
                        !p.getFechaRegistro().isAfter(fin))
            .count();
        reporte.setNuevosPacientes(nuevosPacientes);
        
        // Agrupar por tipo de documento (convertir ENUM a String)
        Map<String, Long> porTipoDocumento = todosPacientes.stream()
            .collect(Collectors.groupingBy(
                p -> p.getTipoDocumento() != null ? p.getTipoDocumento().toString() : "DESCONOCIDO",
                Collectors.counting()
            ));
        reporte.setPacientesPorTipoDocumento(porTipoDocumento);
        
        // Agrupar por estado (convertir ENUM a String)
        Map<String, Long> porEstado = todosPacientes.stream()
            .collect(Collectors.groupingBy(
                p -> p.getEstado() != null ? p.getEstado().toString() : "DESCONOCIDO",
                Collectors.counting()
            ));
        reporte.setPacientesPorEstado(porEstado);
        
        return reporte;
    }
    
    @Override
    @Transactional(readOnly = true)
    public ReporteDoctoresDTO generarReporteDoctores(LocalDate fechaInicio, LocalDate fechaFin) {
        ReporteDoctoresDTO reporte = new ReporteDoctoresDTO();
        
        // Convertir LocalDate a LocalDateTime
        LocalDateTime inicio = fechaInicio != null ? fechaInicio.atStartOfDay() : LocalDateTime.now().minusMonths(1);
        LocalDateTime fin = fechaFin != null ? fechaFin.atTime(LocalTime.MAX) : LocalDateTime.now();
        
        // Obtener todos los doctores
        List<Doctor> todosDoctores = doctorRepository.findAll();
        
        reporte.setTotalDoctores((long) todosDoctores.size());
        
        // Contar activos
        long activos = todosDoctores.stream()
            .filter(d -> "ACTIVO".equals(d.getEstado()))
            .count();
        reporte.setDoctoresActivos(activos);
        
        // Agrupar por especialidad
        Map<String, Long> porEspecialidad = todosDoctores.stream()
            .collect(Collectors.groupingBy(Doctor::getEspecialidad, Collectors.counting()));
        reporte.setDoctoresPorEspecialidad(porEspecialidad);
        
        // Obtener citas del período para estadísticas por doctor
        List<Cita> citas = citaRepository.findAll().stream()
            .filter(c -> c.getFechaHora() != null && 
                        !c.getFechaHora().isBefore(inicio) && 
                        !c.getFechaHora().isAfter(fin))
            .collect(Collectors.toList());
        
        // Citas por doctor
        Map<String, Long> citasPorDoctor = citas.stream()
            .filter(c -> c.getDoctor() != null)
            .collect(Collectors.groupingBy(
                c -> c.getDoctor().getNombres() + " " + c.getDoctor().getApellidos(),
                Collectors.counting()
            ));
        reporte.setCitasPorDoctor(citasPorDoctor);
        
        // Promedio de calificación por doctor (de las valoraciones de citas)
        Map<String, Double> promedioCalificacion = new HashMap<>();
        for (Doctor doctor : todosDoctores) {
            List<ValoracionCita> valoraciones = valoracionCitaRepository.findAll().stream()
                .filter(v -> v.getCita() != null && 
                            v.getCita().getDoctor() != null &&
                            v.getCita().getDoctor().getIdDoctor().equals(doctor.getIdDoctor()))
                .collect(Collectors.toList());
            
            if (!valoraciones.isEmpty()) {
                double promedio = valoraciones.stream()
                    .mapToInt(ValoracionCita::getCalificacion)
                    .average()
                    .orElse(0.0);
                promedioCalificacion.put(doctor.getNombres() + " " + doctor.getApellidos(), promedio);
            }
        }
        reporte.setPromedioCalificacionPorDoctor(promedioCalificacion);
        
        return reporte;
    }
}
