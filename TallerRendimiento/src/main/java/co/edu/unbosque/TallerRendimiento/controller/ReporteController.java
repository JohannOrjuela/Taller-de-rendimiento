package co.edu.unbosque.TallerRendimiento.controller;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.TallerRendimiento.dto.ProductoReporteDTO;
import co.edu.unbosque.TallerRendimiento.service.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "Reportes", description = "Operaciones analíticas y pesadas (Síncrono/JPA).")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @Operation(
        summary = "Reporte de Productos Más Vendidos",
        description = "Ejecuta una consulta pesada de agregación (SUM, GROUP BY) sobre las transacciones para obtener los 10 productos más vendidos. **Causa Bloqueo Largo.**",
        responses = @ApiResponse(responseCode = "200", description = "Lista de productos principales con sus métricas.")
    )
    @GetMapping("/top-selling")
    public ResponseEntity<Page<ProductoReporteDTO>> getTopSellingProducts(
            @Parameter(description = "Fecha de inicio para el cálculo del reporte (formato YYYY-MM-DD).")
            @RequestParam(required = false) LocalDate startDate, Pageable pageable) {
        
        LocalDate effectiveStartDate = startDate != null ? startDate : LocalDate.now().minusYears(1);
        Page<ProductoReporteDTO> reporte = reporteService.getTopSellingProducts(effectiveStartDate, pageable);
        
        return ResponseEntity.ok(reporte);
    }
}