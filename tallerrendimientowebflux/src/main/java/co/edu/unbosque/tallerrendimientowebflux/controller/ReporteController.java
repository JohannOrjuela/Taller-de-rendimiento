package co.edu.unbosque.tallerrendimientowebflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.tallerrendimientowebflux.dto.TopSellingReportDTO;
import co.edu.unbosque.tallerrendimientowebflux.service.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "Reportes", description = "Generación de informes para ventas.")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }
    
    @Operation(
        summary = "Reporte de Top Ventas desde una Fecha Exacta",
        description = "Obtiene los 10 productos más vendidos a partir de una fecha específica.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Reporte generado con éxito."),
            @ApiResponse(responseCode = "400", description = "Formato de fecha inválido."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")

        }
    )

    @GetMapping("/top-selling/by-date")
    public Flux<TopSellingReportDTO> getTopSellingProductsByExactDate(
            @Parameter(description = "Fecha inicial en formato YYYY-MM-DD.")
            @RequestParam String startDate) {
        
        return reporteService.findTopSellingProductsByExactDate(startDate);
    }
}