package co.edu.unbosque.TallerRendimiento.dto;

import java.math.BigDecimal;


public class ProductoReporteDTO {

    private final Long idproducto;
    private final String nombreproducto;
    private final Long unidadesvendidas; 
    private final BigDecimal ingresosgenerados; 
    
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