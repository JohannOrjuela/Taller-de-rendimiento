package co.edu.unbosque.tallerrendimientowebflux.repository;

import co.edu.unbosque.tallerrendimientowebflux.model.Comentario;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ComentarioReactiveRepository extends R2dbcRepository<Comentario, Integer> {

    Flux<Comentario> findByIdProducto(Integer idProducto);
}