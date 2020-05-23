package ar.com.yoaprendo.beans;

public class Ubicacion {

    public double latitud;
    public double longitud;

    public Ubicacion() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Ubicacion(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

}
