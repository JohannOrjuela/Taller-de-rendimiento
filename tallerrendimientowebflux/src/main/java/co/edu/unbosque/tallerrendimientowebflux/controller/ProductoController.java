package co.edu.unbosque.tallerrendimientowebflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.tallerrendimientowebflux.dto.DetalleProductoDTO;
import co.edu.unbosque.tallerrendimientowebflux.dto.ProductoDTO;
import co.edu.unbosque.tallerrendimientowebflux.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Productos", description = "Gestión del catálogo de productos y su información detallada.")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(
        summary = "Búsqueda y filtrado de productos",
        description = "Busca productos utilizando una consulta de texto libre, filtrado por categoría y un precio mínimo.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de productos que coinciden con los criterios de búsqueda."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
            
        }
    )
    @GetMapping("/search")
    public Flux<ProductoDTO> searchProducts(
            @Parameter(description = "Término de búsqueda para nombre o descripción del producto.")
            @RequestParam(required = false) String query,
            @Parameter(description = "Nombre de la categoría para filtrar (ej. 'Electrónica').")
            @RequestParam(required = false) String category,
            @Parameter(description = "Precio mínimo del producto.")
            @RequestParam(required = false) Double minPrice) {
        
        return productoService.searchProducts(query, category, minPrice);
    }

    @Operation(
        summary = "Obtener detalles del producto por ID (Vista de concurrencia)",
        description = "Recupera los detalles completos de un producto, incluyendo su calificación promedio, conteo de comentarios, subcategoría y nombre de categoría. Esta es una operación de alta concurrencia (Mono.zip).",
        responses = {
            @ApiResponse(responseCode = "200", description = "Detalles del producto recuperados exitosamente."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
        }
    )
    @GetMapping("/{id}")
    public Mono<DetalleProductoDTO> obtenerDetallesPorId(
            @Parameter(description = "ID del producto a buscar.")
            @PathVariable Integer id) {
        return productoService.obtenerDetallesPorId(id);
    }

}