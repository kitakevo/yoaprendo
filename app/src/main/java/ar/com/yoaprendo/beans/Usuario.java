package ar.com.yoaprendo.beans;

import ar.com.yoaprendo.TipoUsuario;

public class Usuario {

    public String usuario;
    public String clave;
    public TipoUsuario tipoUsuario;

    public Usuario() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Usuario(String usuario, String clave,TipoUsuario tipoUsuario) {
        this.usuario = usuario;
        this.clave = clave;
        this.tipoUsuario = tipoUsuario;
    }

}
