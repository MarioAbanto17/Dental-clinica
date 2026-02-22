package lmas.clinica_dental.service;

import lmas.clinica_dental.dto.*;
import java.time.LocalDate;

public interface IReporteService {
    
    /**
     * Genera un reporte financiero con ingresos, pagos pendientes y métodos de pago
     */
    ReporteFinancieroDTO generarReporteFinanciero(LocalDate fechaInicio, LocalDate fechaFin);
    
    /**
     * Genera un reporte de citas con estadísticas por estado, tipo y doctor
     */
    ReporteCitasDTO generarReporteCitas(LocalDate fechaInicio, LocalDate fechaFin);
    
    /**
     * Genera un reporte de pacientes con totales, activos/inactivos y nuevos registros
     */
    ReportePacientesDTO generarReportePacientes(LocalDate fechaInicio, LocalDate fechaFin);
    
    /**
     * Genera un reporte de doctores con estadísticas de citas y especialidades
     */
    ReporteDoctoresDTO generarReporteDoctores(LocalDate fechaInicio, LocalDate fechaFin);
}
