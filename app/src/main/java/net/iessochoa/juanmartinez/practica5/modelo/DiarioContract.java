package net.iessochoa.juanmartinez.practica5.modelo;

import android.provider.BaseColumns;

public class DiarioContract {

    public static abstract class DiaDiarioEntry {
        //Nombre de nuestra base de datos.
        public static final String TABLE_NAME = "diadiario";

        /* Los campos para los que vamos a crear una columna.
    private Date fecha;
    private int valoracionDia;
    private String resumen;
    private String contenido;
    private String latitud;  <- No lo usamos.
    private String longitud; <- No lo usamos.
    private String fotoUri;
         */

        public static final String _ID = BaseColumns._ID;//esta columna es necesaria para Android
        public static final String FECHA = "fecha";
        public static final String VALORACION = "valoracion";
        public static final String RESUMEN = "resumen";
        public static final String CONTENIDO = "contenido";
        public static final String FOTO_URI = "fotoUri";
    }
}
