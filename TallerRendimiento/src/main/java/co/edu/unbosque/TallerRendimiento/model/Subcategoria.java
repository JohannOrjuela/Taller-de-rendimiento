package co.edu.unbosque.TallerRendimiento.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "subcategoria")
public class Subcategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subcategoria")
    private Integer idSubcategoria;

    @Column(name = "nombre_subcategoria")
    private String nombreSubcategoria;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    public Subcategoria() {
    }

    public Subcategoria(String nombreSubcategoria, Categoria categoria) {
        this.nombreSubcategoria = nombreSubcategoria;
        this.categoria = categoria;
    }

    public Integer getIdSubcategoria() {
        return idSubcategoria;
    }

    public String getNombreSubcategoria() {
        return nombreSubcategoria;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setIdSubcategoria(Integer idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
    }

    public void setNombreSubcategoria(String nombreSubcategoria) {
        this.nombreSubcategoria = nombreSubcategoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

}