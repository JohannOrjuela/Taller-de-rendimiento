package co.edu.unbosque.TallerRendimiento.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import co.edu.unbosque.TallerRendimiento.model.Producto;
import co.edu.unbosque.TallerRendimiento.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/inventory")
@Tag(name = "Gestión de Inventario", description = "Endpoints para la consulta y actualización de stock.")
public class InventoryController {

    @Autowired
    private ProductService productService;

    // ENDPOINT: GET /api/inventory/low-stock
    @GetMapping("/low-stock")
    @Operation(summary = "Productos con Bajo Stock", 
               description = "Lista los productos cuya cantidad actual es menor al umbral especificado.")
    public List<Producto> getLowStockProducts(
            @RequestParam(defaultValue = "10") 
            @Parameter(description = "Umbral de cantidad para considerar stock bajo", example = "5")
            Integer threshold) {
        return productService.obtenerProductosBajoStock(threshold);
    }

    // ENDPOINT: Actualización de stock
    @PutMapping("/{productId}/stock")
    @Operation(summary = "Actualizar Stock", 
               description = "Realiza una entrada o salida de inventario para un producto. Requisito de Óscar: La actualización debe reflejarse en < 400ms.")
    public ResponseEntity<Producto> updateStock(
            @PathVariable 
            @Parameter(description = "ID del producto a modificar", example = "1")
            Integer productId,
            
            @RequestParam 
            @Parameter(description = "Cambio en la cantidad: positivo para entrada, negativo para salida", example = "5")
            Integer quantityChange,
            
            @RequestParam 
            @Parameter(description = "Descripción de la transacción")
            String description,
            
            @RequestParam 
            @Parameter(description = "ID del usuario que realiza la operación", example = "1")
            Integer userId) {

        return productService.actualizarStock(productId, quantityChange, description, userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}