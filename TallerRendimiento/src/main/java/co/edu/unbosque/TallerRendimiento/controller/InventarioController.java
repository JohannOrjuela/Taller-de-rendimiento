package co.edu.unbosque.TallerRendimiento.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.TallerRendimiento.dto.ProductoDTO;
import co.edu.unbosque.TallerRendimiento.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/inventory")
@Tag(name = "Inventario", description = "Operaciones relacionadas con el stock y el inventario de productos (Síncrono/JPA).")
public class InventarioController {

    private final ProductoService productoService;

    public InventarioController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(
        summary = "Obtener productos con bajo stock",
        description = "Recupera una lista de productos cuya cantidad en inventario es menor que el umbral especificado. **Operación Síncrona.**",
        responses = @ApiResponse(responseCode = "200", description = "Lista de productos con bajo stock.")
    )
    @GetMapping("/low-stock")
    public ResponseEntity<List<ProductoDTO>> findLowStockProducts(
            @Parameter(description = "Umbral máximo de stock para considerar un producto como 'bajo stock'.")
            @RequestParam(defaultValue = "10") Integer threshold) {
        
        List<ProductoDTO> lowStock = productoService.getLowStockProducts(threshold);
        return ResponseEntity.ok(lowStock);
    }

    @Operation(
        summary = "Actualizar stock de UN solo producto (por URL)",
        description = "Recibe el producto y la cantidad como parámetros de consulta. **Latencia < 400ms.**",
        responses = {
            @ApiResponse(responseCode = "200", description = "Stock actualizado correctamente."),
            @ApiResponse(responseCode = "400", description = "Solicitud o datos inválidos.")
        }
    )
    @PutMapping("/receive-merchandise")
    public ResponseEntity<Void> receiveMerchandise(
        
        @Parameter(description = "ID del producto a actualizar.")
        @RequestParam(name = "idProducto") Integer idProducto,
        
        @Parameter(description = "Cantidad recibida (stock que entra).")
        @RequestParam(name = "cantidadRecibida") Integer cantidadRecibida,
        
        @Parameter(description = "ID del usuario que realiza la operación.")
        @RequestParam(name = "userId") Integer idUsuarioLogueado) { 
        
        // El Controller pasa los tres parámetros al servicio.
        productoService.actualizarStockPorRecepcionUnitaria(idProducto, cantidadRecibida, idUsuarioLogueado);
        
        return ResponseEntity.ok().build();
    }
}