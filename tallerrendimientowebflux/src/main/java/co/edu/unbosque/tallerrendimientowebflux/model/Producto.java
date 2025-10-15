package co.edu.unbosque.tallerrendimientowebflux.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table; 
@Table("producto")
public class Producto {

    @Id
    @Column("id_producto")
    private final Integer idProducto;

    @Column("nombre_producto")
    private final String nombreProducto;

    @Column("precio_producto")
    private final BigDecimal precioProducto;

    @Column("descripcion_producto")
    private final String descripcionProducto;

    @Column("cantidad_producto")
    private final Integer cantidadProducto;

    @Column("id_subcategoria") 
    private final Integer idSubcategoria;


    public Producto(Integer idProducto, String nombreProducto, BigDecimal precioProducto, String descripcionProducto,
            Integer cantidadProducto, Integer idSubcategoria) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.descripcionProducto = descripcionProducto;
        this.cantidadProducto = cantidadProducto;
        this.idSubcategoria = idSubcategoria;
    }
    // AGREGAR ESTE CONSTRUCTOR A TU CLASE Producto.java
    public Producto(String nombreProducto, BigDecimal precioProducto, String descripcionProducto,
                    Integer cantidadProducto, Integer idSubcategoria) {
        // El ID se inicializa a null para que R2DBC lo genere automáticamente al guardar
        this.idProducto = null; 
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.descripcionProducto = descripcionProducto;
        this.cantidadProducto = cantidadProducto;
        this.idSubcategoria = idSubcategoria;
    }
    public Integer getIdProducto() {
        return idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public BigDecimal getPrecioProducto() {
        return precioProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public Integer getCantidadProducto() {
        return cantidadProducto;
    }

    public Integer getIdSubcategoria() {
        return idSubcategoria;
    }


}