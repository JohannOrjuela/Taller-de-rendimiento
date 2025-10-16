package co.edu.unbosque.tallerrendimientowebflux.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

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

    public Comentario(String contenidoComentario, LocalDate fechaComentario, Integer idComentario, Integer idProducto) {
        this.contenidoComentario = contenidoComentario;
        this.fechaComentario = fechaComentario;
        this.idComentario = idComentario;
        this.idProducto = idProducto;
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

}
