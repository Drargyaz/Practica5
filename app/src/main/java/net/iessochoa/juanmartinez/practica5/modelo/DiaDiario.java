package net.iessochoa.juanmartinez.practica5.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class DiaDiario implements Parcelable {

    // Campos de nuestra base de datos.
    private Date fecha;
    private int valoracionDia;
    private String resumen;
    private String contenido;
    private String latitud;
    private String longitud;
    private String fotoUri;

    // Constructor completo.
    public DiaDiario(Date fecha, int valoracionDia, String resumen, String contenido, String latitud, String longitud, String fotoUri) {
        this.fecha = fecha;
        this.valoracionDia = valoracionDia;
        this.resumen = resumen;
        this.contenido = contenido;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fotoUri = fotoUri;
    }

    // Constructor sin foto y sin coordenadas.
    public DiaDiario(Date fecha, int valoracionDia, String resumen, String contenido) {
        this.fecha = fecha;
        this.valoracionDia = valoracionDia;
        this.resumen = resumen;
        this.contenido = contenido;
    }

    // GETTERS Y SETTERS.
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getValoracionDia() {
        return valoracionDia;
    }

    public void setValoracionDia(int valoracionDia) {
        this.valoracionDia = valoracionDia;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getFotoUri() {
        return fotoUri;
    }

    public void setFotoUri(String fotoUri) {
        this.fotoUri = fotoUri;
    }

    public int getValoracionResumida(int valoracionDia){
        if(valoracionDia < 5)
            return 1;
        else if(valoracionDia < 8 && valoracionDia >  5)
            return 2;
        else
            return 3;
    }

    // ********  P A R C E L A B L E ********
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.fecha != null ? this.fecha.getTime() : -1);
        dest.writeInt(this.valoracionDia);
        dest.writeString(this.resumen);
        dest.writeString(this.contenido);
        dest.writeString(this.latitud);
        dest.writeString(this.longitud);
        dest.writeString(this.fotoUri);
    }

    protected DiaDiario(Parcel in) {
        long tmpFecha = in.readLong();
        this.fecha = tmpFecha == -1 ? null : new Date(tmpFecha);
        this.valoracionDia = in.readInt();
        this.resumen = in.readString();
        this.contenido = in.readString();
        this.latitud = in.readString();
        this.longitud = in.readString();
        this.fotoUri = in.readString();
    }

    public static final Parcelable.Creator<DiaDiario> CREATOR = new Parcelable.Creator<DiaDiario>() {
        @Override
        public DiaDiario createFromParcel(Parcel source) {
            return new DiaDiario(source);
        }

        @Override
        public DiaDiario[] newArray(int size) {
            return new DiaDiario[size];
        }
    };
}
