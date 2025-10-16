package co.edu.unbosque.TallerRendimiento.repository;

import co.edu.unbosque.TallerRendimiento.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

   
Optional<Producto> findById(Integer id); 

  
 @Query("SELECT p FROM Producto p " +  "JOIN p.subcategoria s " + "JOIN s.categoria c " + 
        "WHERE (LOWER(p.nombreProducto) LIKE LOWER(CONCAT('%', COALESCE(:query, p.nombreProducto), '%')) OR :query IS NULL) " +
        "AND (LOWER(c.nombreCategoria) = LOWER(COALESCE(:category, c.nombreCategoria)) OR :category IS NULL) " +
        "AND (:minPrice IS NULL OR p.precioProducto >= :minPrice)")
  
  List<Producto> searchProducts(
    @Param("query") String query,
    @Param("category") String category,
    @Param("minPrice") BigDecimal minPrice);

    List<Producto> findByCantidadProductoLessThan(Integer threshold);

}