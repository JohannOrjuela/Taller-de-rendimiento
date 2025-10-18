package co.edu.unbosque.TallerRendimiento.controller;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.TallerRendimiento.dto.DetalleProductoDTO;
import co.edu.unbosque.TallerRendimiento.dto.ProductoDTO;
import co.edu.unbosque.TallerRendimiento.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Productos", description = "Operaciones de búsqueda y detalle de productos (Síncrono/JPA).")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(
        summary = "Buscar productos con filtros",
        description = "Permite buscar productos por texto, categoría y precio mínimo. **Operación Síncrona.**",
        responses = @ApiResponse(responseCode = "200", description = "Lista de productos encontrados.")
    )
    @GetMapping("/search")
    public ResponseEntity<Page<ProductoDTO>> searchProducts(
            @Parameter(description = "Texto parcial para buscar en el nombre del producto.")
            @RequestParam(required = false) String query,
            @Parameter(description = "Nombre exacto de la categoría para filtrar.")
            @RequestParam(required = false) String category,
            @Parameter(description = "Precio mínimo del producto.")
            @RequestParam(required = false) BigDecimal minPrice,
            Pageable pageable) {
        
        Page<ProductoDTO> productos = productoService.searchProducts(query, category, minPrice, pageable);
        return ResponseEntity.ok(productos);
    }

    @Operation(
        summary = "Obtener detalles completos de un producto",
        description = "Recupera un producto con su información de calificaciones y comentarios. **Causa Múltiples Bloqueos Secuenciales (N+1) en el Service.**",
        responses = {
            @ApiResponse(responseCode = "200", description = "Detalles del producto cargados."),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado.")
        }
    )
    @GetMapping("/{id}/details")
    public ResponseEntity<DetalleProductoDTO> getProductDetails(
            @Parameter(description = "ID del producto a buscar.")
            @PathVariable Integer id) {
        
        DetalleProductoDTO detalles = productoService.getProductDetails(id);
        return ResponseEntity.ok(detalles);
    }
}