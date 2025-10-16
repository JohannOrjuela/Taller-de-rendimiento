package co.edu.unbosque.TallerRendimiento.repository;

import co.edu.unbosque.TallerRendimiento.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {

   
    List<Comentario> findByProductoIdProducto(Integer idProducto); 
}