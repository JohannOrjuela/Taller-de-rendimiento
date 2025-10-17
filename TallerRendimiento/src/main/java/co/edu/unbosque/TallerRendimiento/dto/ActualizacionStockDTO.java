package co.edu.unbosque.TallerRendimiento.dto;

public class ActualizacionStockDTO {
    
    private Integer idProducto;
    private Integer cantidadRecibida;
    private Integer idUsuario;

    public ActualizacionStockDTO(Integer cantidadRecibida, Integer idProducto, Integer idUsuario) {
        this.cantidadRecibida = cantidadRecibida;
        this.idProducto = idProducto;
        this.idUsuario = idUsuario;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public Integer getCantidadRecibida() {
        return cantidadRecibida;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public void setCantidadRecibida(Integer cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }


}