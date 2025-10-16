package co.edu.unbosque.TallerRendimiento.dto;

import java.time.LocalDate;

public class ComentarioDTO {

    private final String contenido;
    private final LocalDate fecha;

    public ComentarioDTO(String contenido, LocalDate fecha) {
        this.contenido = contenido;
        this.fecha = fecha;
    }
  
    public String getContenido() {
        return contenido;
    }

    public LocalDate getFecha() {
        return fecha;
    }

}