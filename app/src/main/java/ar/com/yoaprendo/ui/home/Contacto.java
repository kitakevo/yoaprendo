package ar.com.yoaprendo.ui.home;

public class Contacto {

    private String nombre;
    private String fotoUrl;

    public Contacto() {
    }

    public Contacto(String nombre, String fotoCadena) {
        this.nombre = nombre;
        this.fotoUrl = fotoCadena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getfotoUrl() {
        return fotoUrl;
    }

    public void setfotoUrl(String fotoCadena) {
        this.fotoUrl = fotoCadena;
    }
}
