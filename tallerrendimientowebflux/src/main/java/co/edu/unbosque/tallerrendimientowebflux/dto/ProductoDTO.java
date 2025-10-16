package co.edu.unbosque.tallerrendimientowebflux.dto;

import java.math.BigDecimal;

import org.springframework.data.annotation.PersistenceCreator;

public class ProductoDTO {
    
    private final Integer id;
    private final String nombre;
    private final String descripcion;
    private final BigDecimal precio; 
    private final Integer inventario;
    private final Integer idSubcategoria;
    private final String nombreSubcategoria;

    @PersistenceCreator
    public ProductoDTO(Integer id, String nombre, String descripcion, 
                       BigDecimal precio, Integer inventario, 
                       Integer idSubcategoria, String nombreSubcategoria) { 
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.inventario = inventario;
        this.idSubcategoria = idSubcategoria;
        this.nombreSubcategoria = nombreSubcategoria;
    }
    
    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public Integer getInventario() {
        return inventario;
    }

    public Integer getIdSubcategoria() {
        return idSubcategoria;
    }

    public String getNombreSubcategoria() {
        return nombreSubcategoria;
    }
}