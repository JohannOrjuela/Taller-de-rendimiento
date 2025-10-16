package co.edu.unbosque.tallerrendimientowebflux.dto;

import java.math.BigDecimal;

import org.springframework.data.annotation.PersistenceCreator;

public class ProductoReporteDTO {

    private final Long idproducto;
    private final String nombreproducto;
    private final Long unidadesvendidas; 
    private final BigDecimal ingresosgenerados; 
    
    @PersistenceCreator
    public ProductoReporteDTO(Long idproducto, String nombreproducto, Long unidadesvendidas, BigDecimal ingresosgenerados) {
        this.idproducto = idproducto;
        this.nombreproducto = nombreproducto;
        this.unidadesvendidas = unidadesvendidas;
        this.ingresosgenerados = ingresosgenerados;
    }

    public Long getIdProducto() { 
        return idproducto; 
    }
    public String getNombreProducto() { 
        return nombreproducto; 
    }
    public Long getUnidadesVendidas() { 
        return unidadesvendidas; 
    }
    public BigDecimal getIngresosGenerados() { 
        return ingresosgenerados; 
    }
}