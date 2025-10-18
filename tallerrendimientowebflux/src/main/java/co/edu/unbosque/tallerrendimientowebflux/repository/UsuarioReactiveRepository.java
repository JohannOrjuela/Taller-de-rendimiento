package co.edu.unbosque.tallerrendimientowebflux.repository;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.tallerrendimientowebflux.model.Usuario;

@Repository
public interface  UsuarioReactiveRepository extends R2dbcRepository<Usuario, Integer> {

}
