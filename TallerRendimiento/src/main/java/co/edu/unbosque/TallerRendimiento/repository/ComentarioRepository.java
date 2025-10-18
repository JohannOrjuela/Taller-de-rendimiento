package co.edu.unbosque.TallerRendimiento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.unbosque.TallerRendimiento.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {

     @Query(value = "SELECT * FROM Comentario c WHERE c.id_producto = :idProducto " +
                   "ORDER BY c.fecha_comentario DESC LIMIT 10", 
           nativeQuery = true)
    List<Comentario> findByProductoIdProducto(Integer idProducto); 
}