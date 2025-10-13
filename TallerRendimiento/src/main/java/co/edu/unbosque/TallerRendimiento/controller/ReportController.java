package co.edu.unbosque.TallerRendimiento.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import co.edu.unbosque.TallerRendimiento.model.Producto;
import co.edu.unbosque.TallerRendimiento.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "Reportes de Negocio", description = "Endpoints para la generación de reportes analíticos.")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // ENDPOINT: GET /api/reports/top-selling
    @GetMapping("/top-selling")
    @Operation(summary = "Productos más Vendidos", 
               description = "Genera un reporte de los productos con mayores ventas en un periodo dado. Requisito de Camila: < 700ms.")
    public List<Producto> getTopSellingProducts(
            @RequestParam(defaultValue = "2024-01-01")
            @Parameter(description = "Fecha de inicio del reporte (AAAA-MM-DD)")
            LocalDate startDate)  {
        return reportService.obtenerProductosMasVendidos(startDate);
    }
}