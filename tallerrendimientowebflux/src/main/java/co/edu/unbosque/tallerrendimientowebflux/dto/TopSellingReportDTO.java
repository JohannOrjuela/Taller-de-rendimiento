package co.edu.unbosque.tallerrendimientowebflux.dto;

public class TopSellingReportDTO {

    private final Long idProducto;
    private final String nombreProducto;
    private final Long unidadesVendidas; // Calculado (COUNT de Trans_Inventario)
    private final Double ingresosGenerados; // Calculado (SUM)

    public TopSellingReportDTO(Long idProducto, String nombreProducto, Long unidadesVendidas, Double ingresosGenerados) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.unidadesVendidas = unidadesVendidas;
        this.ingresosGenerados = ingresosGenerados;
    }

    // Getters (solo lectura)
    public Long getIdProducto() { 
        return idProducto; 
    }
    public String getNombreProducto() { 
        return nombreProducto; 
    }
    public Long getUnidadesVendidas() { 
        return unidadesVendidas; 
    }
    public Double getIngresosGenerados() { 
        return ingresosGenerados; 
    }
}