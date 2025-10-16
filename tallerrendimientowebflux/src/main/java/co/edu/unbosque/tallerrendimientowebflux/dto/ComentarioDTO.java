package co.edu.unbosque.tallerrendimientowebflux.dto;

import java.time.LocalDate;

public class ComentarioDTO {

    private final String contenido;
    private final LocalDate fecha;
    private final String usuarioNombre; 

    
    public ComentarioDTO(String contenido, LocalDate fecha, String usuarioNombre) {
        this.contenido = contenido;
        this.fecha = fecha;
        this.usuarioNombre = usuarioNombre;
    }
    
    public String getContenido() {
        return contenido;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

}