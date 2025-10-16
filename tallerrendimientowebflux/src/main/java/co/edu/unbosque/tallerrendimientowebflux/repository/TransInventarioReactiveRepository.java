package co.edu.unbosque.tallerrendimientowebflux.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.tallerrendimientowebflux.dto.ProductoReporteDTO;
import co.edu.unbosque.tallerrendimientowebflux.model.TransInventario;
import reactor.core.publisher.Flux;

@Repository
public interface TransInventarioReactiveRepository extends R2dbcRepository<TransInventario, Integer> {

    
  @Query("""
            SELECT 
                p.id_producto AS idproducto,             
                p.nombre_producto AS nombreproducto,    
                CAST(SUM(ti.cantidad_trans_inventario) AS BIGINT) AS unidadesvendidas, 
                CAST(SUM(ti.cantidad_trans_inventario * p.precio_producto) AS NUMERIC) AS ingresosgenerados 
            FROM trans_inventario ti
            JOIN producto p ON ti.id_producto = p.id_producto
            WHERE ti.tipo_trans_inventario = 'Venta' 
              AND ti.fecha_trans_inventario >= CAST($1 AS DATE) 
            GROUP BY p.id_producto, p.nombre_producto, p.precio_producto
            ORDER BY unidadesvendidas DESC
            LIMIT 10
            """)
    Flux<ProductoReporteDTO> findTopSellingProducts(String startDate);
}