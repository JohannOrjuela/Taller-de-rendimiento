package co.edu.unbosque.tallerrendimientowebflux.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("categoria")
public class Categoria {

    @Id
    @Column("id_categoria")
    private final Integer idCategoria;

    @Column("nombre_categoria")
    private final String nombreCategoria;

    public Categoria(Integer idCategoria, String nombreCategoria) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

}