package co.edu.unbosque.tallerrendimientowebflux.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDate;

@Table("trans_inventario")
public class TransInventario {

    @Id
    @Column("id_trans_inventario")
    private final Integer idTransInventario;

    @Column("tipo_trans_inventario")
    private final String tipoTransInventario;

    @Column("fecha_trans_inventario")
    private final LocalDate fechaTransInventario;

    @Column("cantidad_trans_inventario")
    private final Integer cantidadTransInventario;

    @Column("descripcion_trans_inventario")
    private final String descripcionTransInventario;

    @Column("id_producto") 
    private final Integer idProducto;

    @Column("id_usuario") 
    private final Integer idUsuario;

    
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

   
}
