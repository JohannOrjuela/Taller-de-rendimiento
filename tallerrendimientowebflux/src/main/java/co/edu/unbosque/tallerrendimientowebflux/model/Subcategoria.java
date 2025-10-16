package co.edu.unbosque.tallerrendimientowebflux.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("subcategoria")
public class Subcategoria {

    @Id
    @Column("id_subcategoria")
    private final Integer idSubcategoria;

    @Column("nombre_subcategoria")
    private final String nombreSubcategoria;

    @Column("id_categoria") 
    private final Integer idCategoria;

    public Subcategoria(Integer idSubcategoria, String nombreSubcategoria, Integer idCategoria) {
        this.idSubcategoria = idSubcategoria;
        this.nombreSubcategoria = nombreSubcategoria;
        this.idCategoria = idCategoria;
    }

    public Integer getIdSubcategoria() {
        return idSubcategoria;
    }

    public String getNombreSubcategoria() {
        return nombreSubcategoria;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    
}