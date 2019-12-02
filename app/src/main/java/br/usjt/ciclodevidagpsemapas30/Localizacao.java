package br.usjt.ciclodevidagpsemapas30;

import java.util.ArrayList;
import java.util.List;

public class Localizacao {
    private static double latitude;
    private static double longitude;
    private List<Localizacao> lista = new ArrayList<>(50);

    public Localizacao(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public List<Localizacao> getLista() {
        return lista;
    }

    public void setLista(List<Localizacao> lista) {
        this.lista = lista;
    }

    public static double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public static double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    @Override
    public String toString() {
        return "Latitude: " + latitude + " Longitude: " + longitude + "";
    }
}
