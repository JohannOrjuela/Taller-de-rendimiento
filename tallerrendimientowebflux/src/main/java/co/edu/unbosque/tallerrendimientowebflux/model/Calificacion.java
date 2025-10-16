package co.edu.unbosque.tallerrendimientowebflux.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDate;

@Table("calificacion")
public class Calificacion {

    @Id 
    @Column("id_calificacion")
    private final Integer id; 

    @Column("valor_calificacion")
    private final Double valorClasificacion; 

    @Column("fecha_calificacion")
    private final LocalDate fechaCalificacion;

    @Column("id_producto")
    private final Integer idProducto; 
    
     public Calificacion(Integer id, Double valorClasificacion, LocalDate fechaCalificacion, Integer idProducto) {
        this.id = id;
        this.valorClasificacion = valorClasificacion;
        this.fechaCalificacion = fechaCalificacion;
        this.idProducto = idProducto;
    }

     public Integer getId() {
         return id;
     }

     public Double getValorClasificacion() {
         return valorClasificacion;
     }

     public LocalDate getFechaCalificacion() {
         return fechaCalificacion;
     }

     public Integer getIdProducto() {
         return idProducto;
     }
    
}