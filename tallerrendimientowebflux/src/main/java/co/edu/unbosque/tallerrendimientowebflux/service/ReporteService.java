package co.edu.unbosque.tallerrendimientowebflux.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import co.edu.unbosque.tallerrendimientowebflux.dto.ProductoReporteDTO;
import co.edu.unbosque.tallerrendimientowebflux.repository.TransInventarioReactiveRepository;
import reactor.core.publisher.Flux;

@Service
public class ReporteService {

    private final TransInventarioReactiveRepository transInventarioRepository;

    public ReporteService(TransInventarioReactiveRepository transInventarioRepository) {
        this.transInventarioRepository = transInventarioRepository;
    }

    // -----------------------------------------------------------------
    //TOP VENTAS POR FECHA EXACTA (/reports/top-selling/by-date)
    // -----------------------------------------------------------------
    public Flux<ProductoReporteDTO> findTopSellingProductsByExactDate(String startDate) { 
        try {
            LocalDate startDateString = LocalDate.parse(startDate);
            return transInventarioRepository.findTopSellingProducts(startDate); 
        
        } catch (DateTimeParseException e) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "Formato de fecha inválido. Use el formato YYYY-MM-DD.", 
                e
            );
        }
        
    }
}