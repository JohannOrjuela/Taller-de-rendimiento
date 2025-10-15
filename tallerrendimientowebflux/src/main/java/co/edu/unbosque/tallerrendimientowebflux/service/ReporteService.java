package co.edu.unbosque.tallerrendimientowebflux.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import co.edu.unbosque.tallerrendimientowebflux.dto.TopSellingReportDTO;
import co.edu.unbosque.tallerrendimientowebflux.repository.TransInventarioReactiveRepository;
import reactor.core.publisher.Flux;

@Service
public class ReporteService {

    private final TransInventarioReactiveRepository transInventarioRepository;

    public ReporteService(TransInventarioReactiveRepository transInventarioRepository) {
        this.transInventarioRepository = transInventarioRepository;
    }

    // -----------------------------------------------------------------
    // 1. TOP VENTAS POR PERIODO DINÁMICO (/reports/top-selling?period=...)
    // -----------------------------------------------------------------
    public Flux<TopSellingReportDTO> findTopSellingProductsByPeriod(String period) { 
        
        // Determina la fecha inicial (ej. el inicio del mes o año)
        LocalDate startDate = determineStartDate(period);
        
        // Convierte a String para coincidir con la firma del Repositorio (es un requerimiento del SQL)
        String startDateString = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        
        return transInventarioRepository.findTopSellingProducts(startDateString); 
    }

    // -----------------------------------------------------------------
    // 2. TOP VENTAS POR FECHA EXACTA (/reports/top-selling/by-date?startDate=...)
    // -----------------------------------------------------------------
    public Flux<TopSellingReportDTO> findTopSellingProductsByExactDate(String startDate) { 
        
        // Simplemente pasa la fecha como String, asumiendo que ya está en un formato ISO válido (YYYY-MM-DD)
        return transInventarioRepository.findTopSellingProducts(startDate); 
    }
    
    // -----------------------------------------------------------------
    // LÓGICA AUXILIAR
    // -----------------------------------------------------------------
    
    private LocalDate determineStartDate(String period) {
        if ("year".equalsIgnoreCase(period)) {
            // Primer día del año pasado
            return LocalDate.now().minusYears(1).withDayOfMonth(1).withDayOfYear(1);
        } else if ("month".equalsIgnoreCase(period)) {
            // Primer día del mes pasado
            return LocalDate.now().minusMonths(1).withDayOfMonth(1);
        } else {
            // Valor por defecto: Primer día del mes actual para una buena cantidad de datos
            return LocalDate.now().withDayOfMonth(1); 
        }
    }
}