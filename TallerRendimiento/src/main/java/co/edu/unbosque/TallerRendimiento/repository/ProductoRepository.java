package co.edu.unbosque.TallerRendimiento.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.TallerRendimiento.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    // GET /api/products/search: Búsqueda sin optimización (LIKE '%producto%') 
    @Query("SELECT p FROM Producto p " +
           "JOIN p.subcategoria s " +
           "JOIN s.categoria c " +
           "WHERE (:query IS NULL OR LOWER(p.nombreProducto) LIKE LOWER(CONCAT('%', :query, '%'))) " +
           "AND (:category IS NULL OR LOWER(c.nombreCategoria) = LOWER(:category)) " +
           "AND (:minPrice IS NULL OR p.precioProducto >= :minPrice)")
    List<Producto> searchProducts(
            @Param("query") String query,
            @Param("category") String category,
            @Param("minPrice") BigDecimal minPrice);

    // GET /api/inventory/low-stock
    List<Producto> findByCantidadProductoLessThan(Integer threshold);

    // GET /api/reports/top-selling: [cite: 72]
    
    @Query(value = "SELECT p.* FROM producto p " +
                "JOIN trans_inventario ti ON p.id_producto = ti.id_producto " +
                "WHERE ti.tipo_trans_inventario = 'Venta' " +
                "AND ti.fecha_trans_inventario::DATE >= :startDate " +  // <- Caster el TIMESTAMP a DATE
                "GROUP BY p.id_producto " +
                "ORDER BY SUM(ti.cantidad_trans_inventario) DESC " +
                "LIMIT 10", nativeQuery = true)
    List<Producto> findTopSellingProducts(@Param("startDate") LocalDate startDate);
}