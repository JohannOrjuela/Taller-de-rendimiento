package co.edu.unbosque.tallerrendimientowebflux.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("trans_inventario")
public class TransInventario {

    @Id
    @Column("id_trans_inventario")
    private Integer idTransInventario;

    @Column("tipo_trans_inventario")
    private String tipoTransInventario;

    @Column("fecha_trans_inventario")
    private LocalDate fechaTransInventario;

    @Column("cantidad_trans_inventario")
    private Integer cantidadTransInventario;

    @Column("descripcion_trans_inventario")
    private String descripcionTransInventario;

    @Column("id_producto") 
    private Integer idProducto;

    @Column("id_usuario") 
    private Integer idUsuario;

    
    public TransInventario(Integer idTransInventario, String tipoTransInventario, LocalDate fechaTransInventario,
            Integer cantidadTransInventario, String descripcionTransInventario, Integer idProducto, Integer idUsuario) {
        this.idTransInventario = idTransInventario;
        this.tipoTransInventario = tipoTransInventario;
        this.fechaTransInventario = fechaTransInventario;
        this.cantidadTransInventario = cantidadTransInventario;
        this.descripcionTransInventario = descripcionTransInventario;
        this.idProducto = idProducto;
        this.idUsuario = idUsuario;
    }

    public TransInventario() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public Integer getIdTransInventario() {
        return idTransInventario;
    }


    public String getTipoTransInventario() {
        return tipoTransInventario;
    }


    public LocalDate getFechaTransInventario() {
        return fechaTransInventario;
    }


    public Integer getCantidadTransInventario() {
        return cantidadTransInventario;
    }


    public String getDescripcionTransInventario() {
        return descripcionTransInventario;
    }


    public Integer getIdProducto() {
        return idProducto;
    }


    public Integer getIdUsuario() {
        return idUsuario;
    }
   public void setIdTransInventario(Integer idTransInventario) {
        this.idTransInventario = idTransInventario;
    }

    public void setTipoTransInventario(String tipoTransInventario) {
        this.tipoTransInventario = tipoTransInventario;
    }

    public void setFechaTransInventario(LocalDate fechaTransInventario) {
        this.fechaTransInventario = fechaTransInventario;
    }

    public void setCantidadTransInventario(Integer cantidadTransInventario) {
        this.cantidadTransInventario = cantidadTransInventario;
    }

    public void setDescripcionTransInventario(String descripcionTransInventario) {
        this.descripcionTransInventario = descripcionTransInventario;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}
