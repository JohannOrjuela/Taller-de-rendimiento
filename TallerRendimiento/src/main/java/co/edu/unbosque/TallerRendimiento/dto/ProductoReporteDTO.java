package co.edu.unbosque.TallerRendimiento.dto;

import java.math.BigDecimal;


public class ProductoReporteDTO {

    private final Long idProducto;
    private final String nombreProducto;
    private final Long unidadesVendidas; 
    private final BigDecimal ingresosGenerados;  
    
    public ProductoReporteDTO(Long idProducto, String nombreProducto, Long unidadesVendidas, BigDecimal ingresosGenerados) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.unidadesVendidas = unidadesVendidas;
        this.ingresosGenerados = ingresosGenerados;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public Long getUnidadesVendidas() {
        return unidadesVendidas;
    }

    public BigDecimal getIngresosGenerados() {
        return ingresosGenerados;
    }


}