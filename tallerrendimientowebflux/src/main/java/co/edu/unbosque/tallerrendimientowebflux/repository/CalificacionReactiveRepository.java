package co.edu.unbosque.tallerrendimientowebflux.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.tallerrendimientowebflux.model.Calificacion;
import reactor.core.publisher.Mono;


@Repository
public interface CalificacionReactiveRepository extends R2dbcRepository<Calificacion, Integer> {

    @Query("SELECT AVG(valor_calificacion) FROM calificacion WHERE id_producto = :idProducto")
    Mono<Double> findAverageByProductoId(Integer idProducto);

    @Query("SELECT COUNT(id_calificacion) FROM calificacion WHERE id_producto = :idProducto")
    Mono<Long> countByIdProducto(Integer idProducto);
}