package co.edu.unbosque.tallerrendimientowebflux.dto;

import java.util.List;

public class DetalleProductoDTO {

    private final Long id;
    private final String nombre;
    private final String descripcion;
    private final Double precio;
    private final Integer stock;
    private final String nombreSubcategoria;
    private final String nombreCategoria;
    private final Double promedioCalificacion; // Calculado (AVG)
    private final Long totalComentarios;       // Calculado (COUNT)
    private final List<ComentarioDTO> comentarios; // Lista anidada

    public DetalleProductoDTO(Long id, String nombre, String descripcion, Double precio, Integer stock, String nombreSubcategoria, String nombreCategoria, Double promedioCalificacion, Long totalComentarios, List<ComentarioDTO> comentarios) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.nombreSubcategoria = nombreSubcategoria;
        this.nombreCategoria = nombreCategoria;
        this.promedioCalificacion = promedioCalificacion;
        this.totalComentarios = totalComentarios;
        this.comentarios = comentarios;
    }

    public Long getId() { 
        return id; 
    }
    public String getNombre() { 
        return nombre; 
    }
    public String getDescripcion() { 
        return descripcion; 
    }
    public Double getPrecio() { 
        return precio; 
    }
    public Integer getStock() { 
        return stock; 
    }
    public String getNombreSubcategoria() { 
        return nombreSubcategoria; 
    }
    public String getNombreCategoria() { 
        return nombreCategoria; 
    }
    public Double getPromedioCalificacion() { 
        return promedioCalificacion; 
    }
    public Long getTotalComentarios() { 
        return totalComentarios; 
    }
    public List<ComentarioDTO> getComentarios() { 
        return comentarios; 
    }
}