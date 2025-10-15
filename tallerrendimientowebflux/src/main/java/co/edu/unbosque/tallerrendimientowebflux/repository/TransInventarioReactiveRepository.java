package co.edu.unbosque.tallerrendimientowebflux.repository;

import co.edu.unbosque.tallerrendimientowebflux.model.TransInventario;
import co.edu.unbosque.tallerrendimientowebflux.dto.TopSellingReportDTO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TransInventarioReactiveRepository extends R2dbcRepository<TransInventario, Integer> {

    // Endpoint GET /api/reports/top-selling
    // NOTA: R2DBC requiere que las columnas de la consulta coincidan con los nombres
    // de los campos del constructor del DTO (TopSellingReportDTO).
    @Query("""
        SELECT p.id_producto AS idProducto,
               p.nombre_producto AS nombreProducto,
               SUM(ti.cantidad_trans_inventario) AS unidadesVendidas,
               SUM(ti.cantidad_trans_inventario * p.precio_producto) AS ingresosGenerados
        FROM trans_inventario ti
        JOIN producto p ON ti.id_producto = p.id_producto
        WHERE ti.tipo_trans_inventario = 'VENTA'
          AND ti.fecha_trans_inventario >= :startDate  
        GROUP BY p.id_producto, p.nombre_producto, p.precio_producto
        ORDER BY unidadesVendidas DESC
        LIMIT 10
    """)
    Flux<TopSellingReportDTO> findTopSellingProducts(String startDate);
}