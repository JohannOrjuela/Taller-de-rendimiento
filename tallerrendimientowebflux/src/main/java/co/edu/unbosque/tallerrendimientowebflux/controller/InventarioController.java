package co.edu.unbosque.tallerrendimientowebflux.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.tallerrendimientowebflux.dto.ProductoDTO;
import co.edu.unbosque.tallerrendimientowebflux.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/inventory")
@Tag(name = "Inventario", description = "Operaciones relacionadas con el stock y el inventario de productos.")
public class InventarioController {

    private final ProductoService productoService;

    public InventarioController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(
        summary = "Obtener productos con bajo stock",
        description = "Recupera una lista de productos cuya cantidad en inventario es menor que el umbral especificado.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de productos con bajo stock.")
        }
    )
    @GetMapping("/low-stock")
    public Flux<ProductoDTO> findLowStockProducts(
            @Parameter(description = "Umbral máximo de stock para considerar un producto como 'bajo stock'.")
            @RequestParam Integer threshold) {
        
        return productoService.findByCantidadProductoLessThan(threshold);
    }

    @Operation(
        summary = "Actualizar stock de UN solo producto (WebFlux/No Bloqueante)",
        description = "Recibe el producto y la cantidad como parámetros de consulta. **¡Optimizado para Concurrencia!**",
        responses = {
            @ApiResponse(responseCode = "200", description = "Stock actualizado correctamente."),
            @ApiResponse(responseCode = "400", description = "Solicitud o datos inválidos.")
        }
    )
    @PutMapping("/receive-merchandise")
    public Mono<ResponseEntity<Object>> receiveMerchandise(
        @RequestParam(name = "idProducto") Integer idProducto,
        @RequestParam(name = "cantidadRecibida") Integer cantidadRecibida,
        @RequestParam(name = "userId") Integer idUsuarioLogueado
    ) { 
        return productoService.actualizarStockPorRecepcionUnitaria(idProducto, cantidadRecibida, idUsuarioLogueado)
            .thenReturn(ResponseEntity.ok().build()).onErrorResume(Throwable.class, e -> { 
                
                if (e instanceof IllegalArgumentException || e instanceof RuntimeException) {
                     return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); 
                }
                return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()); 
            });
    }
}