package co.edu.unbosque.tallerrendimientowebflux.dto;

public class ProductoReporteDTO {

    private final Long idProducto;
    private final String nombreProducto;
    private final Long unidadesVendidas;
    private final Double ingresosGenerados;

    public ProductoReporteDTO(Long idProducto, String nombreProducto, Long unidadesVendidas, Double ingresosGenerados) {
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

    public Double getIngresosGenerados() {
        return ingresosGenerados;
    }
}
