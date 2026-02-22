package lmas.clinica_dental.controller;

import lmas.clinica_dental.dto.*;
import lmas.clinica_dental.service.IReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
public class ReporteController {
    
    @Autowired
    private IReporteService reporteService;
    
    /**
     * Genera reporte financiero
     * GET /api/reportes/financiero?fechaInicio=2024-01-01&fechaFin=2024-12-31
     */
    @GetMapping("/financiero")
    public ResponseEntity<ReporteFinancieroDTO> generarReporteFinanciero(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        
        ReporteFinancieroDTO reporte = reporteService.generarReporteFinanciero(fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }
    
    /**
     * Genera reporte de citas
     * GET /api/reportes/citas?fechaInicio=2024-01-01&fechaFin=2024-12-31
     */
    @GetMapping("/citas")
    public ResponseEntity<ReporteCitasDTO> generarReporteCitas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        
        ReporteCitasDTO reporte = reporteService.generarReporteCitas(fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }
    
    /**
     * Genera reporte de pacientes
     * GET /api/reportes/pacientes?fechaInicio=2024-01-01&fechaFin=2024-12-31
     */
    @GetMapping("/pacientes")
    public ResponseEntity<ReportePacientesDTO> generarReportePacientes(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        
        ReportePacientesDTO reporte = reporteService.generarReportePacientes(fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }
    
    /**
     * Genera reporte de doctores
     * GET /api/reportes/doctores?fechaInicio=2024-01-01&fechaFin=2024-12-31
     */
    @GetMapping("/doctores")
    public ResponseEntity<ReporteDoctoresDTO> generarReporteDoctores(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        
        ReporteDoctoresDTO reporte = reporteService.generarReporteDoctores(fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }
}
