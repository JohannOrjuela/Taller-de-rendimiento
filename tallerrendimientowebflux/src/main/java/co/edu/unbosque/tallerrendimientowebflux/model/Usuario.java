package co.edu.unbosque.tallerrendimientowebflux.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("usuario")
public class Usuario {

    @Id
    @Column("id_usuario")
    private final Integer idUsuario;

    @Column("correo_usuario")
    private final String correoUsuario;

    @Column("nombre_usuario")
    private final String nombreUsuario;

    @Column("contrasena_usuario")
    private final String contrasenaUsuario;

    @Column("tipo_usuario")
    private final String tipoUsuario;

    public Usuario(Integer idUsuario, String correoUsuario, String nombreUsuario, String contrasenaUsuario,
            String tipoUsuario) {
        this.idUsuario = idUsuario;
        this.correoUsuario = correoUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasenaUsuario = contrasenaUsuario;
        this.tipoUsuario = tipoUsuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasenaUsuario() {
        return contrasenaUsuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

   
}