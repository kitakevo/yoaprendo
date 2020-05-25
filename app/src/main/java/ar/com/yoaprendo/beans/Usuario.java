package ar.com.yoaprendo.beans;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import ar.com.yoaprendo.TipoUsuario;

public class Usuario {

    private String usuario;
    private String clave;
    private TipoUsuario tipoUsuario;
    private Ubicacion ubicacion;
    private String nombre;
    private String fotoPerfil;
    private List<String> chatIdList;

    public Usuario() {

    }

    public Usuario(String usuario, String clave, TipoUsuario tipoUsuario) {

        this.usuario = usuario;
        this.clave = clave;
        this.tipoUsuario = tipoUsuario;
        this.ubicacion = new Ubicacion(0,0);
        this.nombre = "preder";
        this.fotoPerfil = "preder";
        this.chatIdList = new ArrayList<>();

    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public List<String> getChatIdList() {
        return chatIdList;
    }

    public void setChatIdList(List<String> chatIdList) {
        this.chatIdList = chatIdList;
    }
}
