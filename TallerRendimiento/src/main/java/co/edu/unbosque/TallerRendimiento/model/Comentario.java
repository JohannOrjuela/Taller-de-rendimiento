package co.edu.unbosque.TallerRendimiento.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "Comentario")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comentario")
    private Integer idComentario;

    @Column(name= "contenido_comentario")
    private String contenidoComentario;

    @Column(name= "fecha_comentario")
    private LocalDate fechaComentario;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable= false)
    private Producto producto;

    public Comentario() {
    }

    public Comentario(String contenidoComentario, LocalDate fechaComentario, Producto producto) {
        this.contenidoComentario = contenidoComentario;
        this.fechaComentario = fechaComentario;
        this.producto = producto;
    }

    public String getContenidoComentario() {
        return contenidoComentario;
    }

    public void setContenidoComentario(String contenidoComentario) {
        this.contenidoComentario = contenidoComentario;
    }

    public LocalDate getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(LocalDate fechaComentario) {
        this.fechaComentario = fechaComentario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Integer idComentario) {
        this.idComentario = idComentario;
    }
    
    



}
