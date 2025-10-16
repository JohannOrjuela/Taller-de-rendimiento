package co.edu.unbosque.tallerrendimientowebflux.repository;

import java.math.BigDecimal;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.tallerrendimientowebflux.model.Producto;
import reactor.core.publisher.Flux;

@Repository
public interface ProductoReactiveRepository extends R2dbcRepository<Producto, Integer> {

    // 1. Endpoint GET /api/products/search
 @Query("""
    SELECT 
        p.id_producto, p.nombre_producto, p.descripcion_producto, 
        p.precio_producto, p.cantidad_producto, p.id_subcategoria
    FROM producto p
    WHERE 
        (:query IS NULL OR p.nombre_producto ILIKE '%' || :query || '%')
        AND 
        (
            :category IS NULL 
            OR p.id_subcategoria IN (SELECT id_subcategoria FROM subcategoria WHERE nombre_subcategoria = :category)
        )
        AND 
        (
            :minPrice IS NULL 
            OR p.precio_producto >= :minPrice
        )
    """)
    Flux<Producto> searchProductsBase(@Param("query") String query, @Param("category") String category, @Param("minPrice") BigDecimal minPrice); 
    
    @Query("""
        SELECT p.id_producto, p.nombre_producto, p.precio_producto, 
               p.descripcion_producto, p.cantidad_producto, p.id_subcategoria
        FROM producto p
        WHERE p.cantidad_producto < :cantidad
        """)
    Flux<Producto> findLowStockProductsBase(@Param("cantidad") Integer cantidad);
}