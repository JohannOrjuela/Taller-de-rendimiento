package co.edu.unbosque.tallerrendimientowebflux.service;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;

import co.edu.unbosque.tallerrendimientowebflux.dto.ProductoReporteDTO;
import reactor.core.publisher.Flux;

@Service
public class ReporteService {

    private final DatabaseClient databaseClient;

    public ReporteService(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    public Flux<ProductoReporteDTO> getTopSellingProductsByExactDate(String startDate) {
        String sql = """
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
        """;

        return databaseClient.sql(sql)
            .bind("startDate", startDate)
            .map(row -> new ProductoReporteDTO(
                row.get("id_producto", Long.class),
                row.get("nombre_producto", String.class),
                row.get("unidades_vendidas", Long.class),
                row.get("ingresos_generados", Double.class)
            ))
            .all();
    }
}