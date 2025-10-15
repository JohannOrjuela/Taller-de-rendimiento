package co.edu.unbosque.tallerrendimientowebflux.repository;

import co.edu.unbosque.tallerrendimientowebflux.model.Calificacion;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CalificacionReactiveRepository extends R2dbcRepository<Calificacion, Integer> {

    // Consulta para el promedio. AVG retorna un Double.
    @Query("SELECT AVG(valor_calificacion) FROM calificacion WHERE id_producto = :idProducto")
    Mono<Double> findAverageByProductoId(Integer idProducto);

    // Consulta para el conteo. COUNT retorna un Long.
    @Query("SELECT COUNT(id_calificacion) FROM calificacion WHERE id_producto = :idProducto")
    Mono<Long> countByIdProducto(Integer idProducto);
}