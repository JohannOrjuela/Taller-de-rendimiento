package co.edu.unbosque.tallerrendimientowebflux.repository;

import co.edu.unbosque.tallerrendimientowebflux.model.Usuario;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioReactiveRepository extends R2dbcRepository<Usuario, Integer> {
    // Los métodos findById(), findAll(), save() son heredados. 
    // findById() es usado por el ProductoService para buscar el nombre.
}