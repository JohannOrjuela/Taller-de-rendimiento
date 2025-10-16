package co.edu.unbosque.TallerRendimiento.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Calificacion")
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_calificacion")
    private Integer idCalificacion;

    @Column(name= "valor_calificacion", precision =2, scale=1)
    private BigDecimal valorClasificacion;

    @Column(name= "fecha_calificacion")
    private LocalDate fechaCalificacion;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable= false)
    private Producto producto;

    public Calificacion() {
    }

    public Calificacion(BigDecimal valorClasificacion, LocalDate fechaCalificacion, Producto producto) {
        this.valorClasificacion = valorClasificacion;
        this.fechaCalificacion = fechaCalificacion;
        this.producto = producto;
    }

    public BigDecimal getValorClasificacion() {
        return valorClasificacion;
    }

    public void setValorClasificacion(BigDecimal valorClasificacion) {
        this.valorClasificacion = valorClasificacion;
    }

    public LocalDate getFechaCalificacion() {
        return fechaCalificacion;
    }

    public void setFechaCalificacion(LocalDate fechaCalificacion) {
        this.fechaCalificacion = fechaCalificacion;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getIdCalificacion() {
        return idCalificacion;
    }

    public void setIdCalificacion(Integer idCalificacion) {
        this.idCalificacion = idCalificacion;
    }

}
