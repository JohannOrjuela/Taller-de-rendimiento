package co.edu.unbosque.tallerrendimientowebflux.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDate;

@Table("comentario")
public class Comentario {

    @Id
    @Column("id_comentario")
    private final Integer idComentario;

    @Column("contenido_comentario")
    private final String contenidoComentario;

    @Column("fecha_comentario")
    private final LocalDate fechaComentario;

    @Column("id_producto") 
    private final Integer idProducto;

    @Column("id_usuario") 
    private final Integer idUsuario;

    public Comentario(Integer idComentario, String contenidoComentario, LocalDate fechaComentario, Integer idProducto,
            Integer idUsuario) {
        this.idComentario = idComentario;
        this.contenidoComentario = contenidoComentario;
        this.fechaComentario = fechaComentario;
        this.idProducto = idProducto;
        this.idUsuario = idUsuario;
    }

    public Integer getIdComentario() {
        return idComentario;
    }

    public String getContenidoComentario() {
        return contenidoComentario;
    }

    public LocalDate getFechaComentario() {
        return fechaComentario;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    } 

    
}
