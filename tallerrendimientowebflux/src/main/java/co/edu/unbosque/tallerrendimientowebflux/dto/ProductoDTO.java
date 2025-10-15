package co.edu.unbosque.tallerrendimientowebflux.dto;



public class ProductoDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer inventario; 
    private Integer idSubcategoria;
    private String nombreSubcategoria;
    public ProductoDTO() {}

    public ProductoDTO(Long id, String nombre, String descripcion, Double precio, Integer inventario, Integer idSubcategoria, String nombreSubcategoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.inventario = inventario;
        this.idSubcategoria = idSubcategoria;
        this.nombreSubcategoria = nombreSubcategoria;
    }

    public Long getId() { 
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() { 
        return nombre; 
    }
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
    public String getDescripcion() { 
        return descripcion; 
    }
    public void setDescripcion(String descripcion) { 
        this.descripcion = descripcion; 
    }
    public Double getPrecio() { 
        return precio; 
    }
    public void setPrecio(Double precio) { 
        this.precio = precio; 
    }
    public Integer getInventario() { 
        return inventario; 
    }
    public void setInventario(Integer inventario) { 
        this.inventario = inventario; 
    }
    public Integer getIdSubcategoria() { 
        return idSubcategoria; 
    }
    public void setIdSubcategoria(Integer idSubcategoria) { 
        this.idSubcategoria = idSubcategoria; 
    }
    public String getNombreSubcategoria() { 
        return nombreSubcategoria; 
    }
    public void setNombreSubcategoria(String nombreSubcategoria) { 
        this.nombreSubcategoria = nombreSubcategoria; 
    }
}