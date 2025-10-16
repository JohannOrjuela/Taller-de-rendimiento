package co.edu.unbosque.TallerRendimiento.model;

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
@Table(name = "trans_inventario")
public class TransInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_trans_inventario")
    private Integer idTransInventario;

    @Column(name = "tipo_trans_inventario")
    private String tipoTransInventario;

    @Column(name = "fecha_trans_inventario")
    private LocalDate fechaTransInventario;

    @Column(name = "cantidad_trans_inventario")
    private Integer cantidadTransInventario;

    @Column(name = "descripcion_trans_inventario")
    private String descripcionTransInventario;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    public TransInventario() {
    }

    public TransInventario(String tipoTransInventario, LocalDate fechaTransInventario, Integer cantidadTransInventario,
            String descripcionTransInventario, Producto producto, Usuario usuario) {
        this.tipoTransInventario = tipoTransInventario;
        this.fechaTransInventario = fechaTransInventario;
        this.cantidadTransInventario = cantidadTransInventario;
        this.descripcionTransInventario = descripcionTransInventario;
        this.producto = producto;
        this.usuario = usuario;
    }

    public String getTipoTransInventario() {
        return tipoTransInventario;
    }

    public void setTipoTransInventario(String tipoTransInventario) {
        this.tipoTransInventario = tipoTransInventario;
    }

    public LocalDate getFechaTransInventario() {
        return fechaTransInventario;
    }

    public void setFechaTransInventario(LocalDate fechaTransInventario) {
        this.fechaTransInventario = fechaTransInventario;
    }

    public Integer getCantidadTransInventario() {
        return cantidadTransInventario;
    }

    public void setCantidadTransInventario(Integer cantidadTransInventario) {
        this.cantidadTransInventario = cantidadTransInventario;
    }

    public String getDescripcionTransInventario() {
        return descripcionTransInventario;
    }

    public void setDescripcionTransInventario(String descripcionTransInventario) {
        this.descripcionTransInventario = descripcionTransInventario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getIdTransInventario() {
        return idTransInventario;
    }

    public void setIdTransInventario(Integer idTransInventario) {
        this.idTransInventario = idTransInventario;
    }

    

}