package co.edu.unbosque.TallerRendimiento.dto;

import java.time.LocalDate;

public class ComentarioDTO {

    private final String contenido;
    private final LocalDate fecha;
    private final String usuarioNombre; // Nombre del usuario que comentó

    // Constructor completo para mapeo de salida
    public ComentarioDTO(String contenido, LocalDate fecha, String usuarioNombre) {
        this.contenido = contenido;
        this.fecha = fecha;
        this.usuarioNombre = usuarioNombre;
    }

    // Getters (solo lectura)
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