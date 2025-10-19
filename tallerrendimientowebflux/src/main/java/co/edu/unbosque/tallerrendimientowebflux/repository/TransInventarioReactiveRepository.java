package co.edu.unbosque.tallerrendimientowebflux.repository;

import java.util.Map;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.tallerrendimientowebflux.model.TransInventario;
import reactor.core.publisher.Flux;

@Repository
public interface TransInventarioReactiveRepository extends R2dbcRepository<TransInventario, Integer> {

@Query("""
    SELECT 
        p.id_producto,
        p.nombre_producto,
        SUM(ABS(ti.cantidad_trans_inventario)) AS unidades_vendidas,
        SUM(ABS(ti.cantidad_trans_inventario) * p.precio_producto) AS ingresos_generados
    FROM trans_inventario ti
    JOIN producto p ON ti.id_producto = p.id_producto
    WHERE UPPER(ti.tipo_trans_inventario) = 'VENTA'
      AND ti.fecha_trans_inventario >= TO_DATE(:startDate, 'YYYY-MM-DD')
    GROUP BY p.id_producto, p.nombre_producto, p.precio_producto
    ORDER BY unidades_vendidas DESC
    LIMIT 10
""")
Flux<Map<String, Object>> findTopSellingProductsRaw(String startDate);
}
