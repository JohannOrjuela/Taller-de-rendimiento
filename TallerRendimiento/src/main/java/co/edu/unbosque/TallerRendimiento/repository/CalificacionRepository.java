package co.edu.unbosque.TallerRendimiento.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import co.edu.unbosque.TallerRendimiento.model.Calificacion;

public interface CalificacionRepository extends JpaRepository<Calificacion, Integer> {


    @Query("SELECT AVG(c.valorClasificacion) FROM Calificacion c WHERE c.producto.idProducto = :idProducto")
    Double findAverageByProductoId(@Param("idProducto") Integer idProducto);
    
    @Query("SELECT COUNT(c) FROM Calificacion c WHERE c.producto.idProducto = :idProducto")
    Long countByIdProducto(@Param("idProducto") Integer idProducto);
}