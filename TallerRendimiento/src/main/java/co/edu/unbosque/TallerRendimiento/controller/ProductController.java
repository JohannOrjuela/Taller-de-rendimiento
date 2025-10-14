package co.edu.unbosque.TallerRendimiento.controller;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.TallerRendimiento.model.Producto;
import co.edu.unbosque.TallerRendimiento.service.ProductService;

// Anotación para el Controller completo
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Catálogo de Productos", description = "Endpoints para la consulta y detalle de productos.")
public class ProductController {

    @Autowired
    private ProductService productService;

    // ✅ ENDPOINT con paginación
    @GetMapping("/search")
    @Operation(summary = "Búsqueda de Productos con paginación",
               description = "Busca productos por nombre, categoría y precio mínimo. Incluye paginación.")
    public Page<Producto> searchProducts(
            @RequestParam(required = false)
            @Parameter(description = "Término de búsqueda parcial (ej: laptop)")
            String query,

            @RequestParam(required = false)
            @Parameter(description = "Nombre de la Categoría (ej: Electrónica)")
            String category,

            @RequestParam(required = false)
            @Parameter(description = "Precio mínimo del producto")
            BigDecimal minPrice,

            // 🔹 Parámetros de paginación
            @RequestParam(defaultValue = "0")
            @Parameter(description = "Número de página (empezando desde 0)")
            int page,

            @RequestParam(defaultValue = "10")
            @Parameter(description = "Cantidad de productos por página")
            int size) {

        Pageable pageable = PageRequest.of(page, size);
        return productService.buscarProductos(query, category, minPrice, pageable);
    }

    // ENDPOINT: GET /api/products/{id}/details
    @GetMapping("/{id}/details")
    @Operation(summary = "Detalles del Producto", 
               description = "Obtiene toda la información, incluyendo comentarios y calificaciones, de un producto específico.")
    public ResponseEntity<Producto> getProductDetails(
            @PathVariable 
            @Parameter(description = "ID del producto a consultar", example = "1") 
            Integer id) {
        
        return productService.obtenerDetallesProducto(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}