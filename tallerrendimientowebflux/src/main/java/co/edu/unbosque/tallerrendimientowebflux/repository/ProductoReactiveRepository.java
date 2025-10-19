package co.edu.unbosque.tallerrendimientowebflux.repository;

import java.math.BigDecimal;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.tallerrendimientowebflux.model.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductoReactiveRepository extends R2dbcRepository<Producto, Integer> {

@Query("""
	SELECT 
		p.id_producto, p.nombre_producto, p.descripcion_producto, 
		p.precio_producto, p.cantidad_producto, p.id_subcategoria
	FROM producto p
	
	LEFT JOIN subcategoria s ON p.id_subcategoria = s.id_subcategoria
	
	WHERE 
		(:query IS NULL OR p.nombre_producto ILIKE '%' || :query || '%')
		AND 
		-- CAMBIO APLICADO AQUÍ: Utilizamos COALESCE para evitar la inferencia de NULL
		(COALESCE(:category, s.nombre_subcategoria) = s.nombre_subcategoria)
		AND 
		(:minPrice IS NULL OR p.precio_producto >= :minPrice)
	""")
Flux<Producto> searchProductsBase(@Param("query") String query, @Param("category") String category, @Param("minPrice") BigDecimal minPrice);


@Query("""
	SELECT p.id_producto, p.nombre_producto, p.precio_producto, 
		p.descripcion_producto, p.cantidad_producto, p.id_subcategoria
	FROM producto p
	WHERE p.cantidad_producto < :cantidad
	""")
Flux<Producto> findLowStockProductsBase(@Param("cantidad") Integer cantidad);

@Modifying
@Query("UPDATE producto SET cantidad_producto = cantidad_producto + :cantidad WHERE id_producto = :idProducto")
Mono<Integer> sumarStockPorIdProducto(Integer idProducto, Integer cantidad);

}