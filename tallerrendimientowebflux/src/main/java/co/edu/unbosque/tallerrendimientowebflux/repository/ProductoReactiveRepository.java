package co.edu.unbosque.tallerrendimientowebflux.repository;


import co.edu.unbosque.tallerrendimientowebflux.model.Producto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import java.math.BigDecimal; // Usamos BigDecimal ya que tu modelo lo usó

@Repository
// La clave primaria es Integer
public interface ProductoReactiveRepository extends R2dbcRepository<Producto, Integer> {

    // Endpoint GET /api/products/search
    @Query("""
        SELECT p.*
        FROM producto p
        JOIN subcategoria s ON p.id_subcategoria = s.id_subcategoria
        JOIN categoria c ON s.id_categoria = c.id_categoria
        WHERE (:query IS NULL OR p.nombre_producto ILIKE CONCAT('%', :query, '%') OR p.descripcion_producto ILIKE CONCAT('%', :query, '%'))
          AND (:categoryName IS NULL OR c.nombre_categoria = :categoryName)
          AND (:minPrice IS NULL OR p.precio_producto >= :minPrice)
    """)
    Flux<Producto> searchProducts(String query, String categoryName, BigDecimal minPrice);


    // Endpoint GET /api/inventory/low-stock
    // Busca productos con una cantidad inferior al umbral (ej: 10)
    Flux<Producto> findByCantidadProductoLessThan(Integer cantidad);
}