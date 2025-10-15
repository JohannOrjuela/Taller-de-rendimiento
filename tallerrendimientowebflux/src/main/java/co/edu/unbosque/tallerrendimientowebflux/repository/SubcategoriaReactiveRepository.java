package co.edu.unbosque.tallerrendimientowebflux.repository;

import co.edu.unbosque.tallerrendimientowebflux.model.Subcategoria;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SubcategoriaReactiveRepository extends R2dbcRepository<Subcategoria, Integer> {

    /**
     * Consulta custom para obtener el nombre de la Categoria, dado el ID de la Categoria.
     * Esto evita crear un Repositorio para Categoria si solo necesitamos el nombre.
     */
    @Query("SELECT c.nombre_categoria FROM categoria c WHERE c.id_categoria = :idCategoria")
    Mono<String> findCategoriaNombreById(Integer idCategoria);
}