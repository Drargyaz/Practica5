package net.iessochoa.juanmartinez.practica5.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import net.iessochoa.juanmartinez.practica5.modelo.DiarioContract.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DiarioDB {

    // CONSTANTES CON LA VERSION Y EL NOMBRE DE NUESTRA BASE DE DATOS.
    private final static int DB_VERSION=1;
    private final static String DB_NOMBRE= "diario.db";

    //Constructor de la clase.
   /* public DiarioDB(Context context){
        super(context, DB_NOMBRE, null, DB_VERSION);
    } */

    // Sentencia de creación de la base de datos.
    private static final String CREATE_TABLE = "CREATE TABLE " + DiaDiarioEntry.TABLE_NAME
            + " (" + DiaDiarioEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DiaDiarioEntry.FECHA + " TEXT UNIQUE NOT NULL, "
            + DiaDiarioEntry.VALORACION + " INTEGET NOT NULL, "
            + DiaDiarioEntry.RESUMEN + " TEXT UNIQUE NOT NULL, "
            + DiaDiarioEntry.CONTENIDO + " TEXT UNIQUE NOT NULL, "
            + DiaDiarioEntry.FOTO_URI + " TEXT UNIQUE NOT NULL,"
            + " UNIQUE (" + DiaDiarioEntry.FECHA + "))";

    // Sentencia de eliminación.
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + DiaDiarioEntry.TABLE_NAME;

    // Insertar en la base de datos.
    private static final String SQL_INSERT = "INSERT INTO " + DiaDiarioEntry.TABLE_NAME +
            " VALUES(" + DiaDiarioEntry.FECHA +
            "," + DiaDiarioEntry.VALORACION +
            "," +DiaDiarioEntry.RESUMEN +
            "," +DiaDiarioEntry.CONTENIDO +
            "," +DiaDiarioEntry.FOTO_URI +
            ") VALUES (?,?,?,?,?)";

    // Actualizar un campo de la base de datos por su fecha.
    private static final String SQL_UPDATE = "UPDATE " + DiaDiarioEntry.TABLE_NAME +
            " SET "
            + DiaDiarioEntry.VALORACION + "=? , "
            + DiaDiarioEntry.RESUMEN + "=? , "
            + DiaDiarioEntry.CONTENIDO + "=? , "
            + DiaDiarioEntry.FOTO_URI + "=? , "
            + " WHERE " + DiaDiarioEntry.FECHA + " = ?";



    // Cambiar el formato de fecha a String y viceversa.
    public static Date fechaBDtoFecha(String f) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = null;
        try {
            fecha = formatoDelTexto.parse(f);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return fecha;
    }

    public static String fechaToFechaDB(Date fecha){
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        return f.format(fecha);
    }

    // Métodos para insertar, abrir, cerrar, borrar, actualizar.
    private DBHelper dbHelper;
    private SQLiteDatabase db;


    public DiarioDB(Context context){ dbHelper = new DBHelper(context); }

    public void open() throws SQLiteException {
        if ((db == null) || (!db.isOpen()))
            db = dbHelper.getWritableDatabase();
    }

    public void close() throws SQLiteException {
        if (db.isOpen())
            db.close();
    }

    public void insertaDia(DiaDiario dia) throws SQLiteException{
        ContentValues values = new ContentValues();
        String fecha = fechaToFechaDB(dia.getFecha());

        values.put(DiaDiarioEntry.FECHA, fecha);
        values.put(DiaDiarioEntry.VALORACION, dia.getValoracionDia());
        values.put(DiaDiarioEntry.RESUMEN, dia.getResumen());
        values.put(DiaDiarioEntry.CONTENIDO, dia.getContenido());
        if(!dia.getFotoUri().equals(""))
            values.put(DiaDiarioEntry.FOTO_URI, dia.getFotoUri());

        db.insertOrThrow(DiaDiarioEntry.TABLE_NAME, null, values);
    }

    public void actualizaDia(DiaDiario dia) throws SQLiteException{
        ContentValues values = new ContentValues();
        String fecha = fechaToFechaDB(dia.getFecha());

        values.put(DiaDiarioEntry.FECHA, fecha);
        values.put(DiaDiarioEntry.VALORACION, dia.getValoracionDia());
        values.put(DiaDiarioEntry.RESUMEN, dia.getResumen());
        values.put(DiaDiarioEntry.CONTENIDO, dia.getContenido());
        if(!dia.getFotoUri().equals(""))
            values.put(DiaDiarioEntry.FOTO_URI, dia.getFotoUri());

        String where = DiaDiarioEntry.FECHA + "=?";
        String[] arg = new String[]{fecha};
        //actualizamos
        db.update(DiaDiarioEntry.TABLE_NAME, values, where, arg);

    }


    public void borraDia(DiaDiario dia) throws SQLiteException {
        String fecha = fechaToFechaDB(dia.getFecha());
        db.delete(DiaDiarioEntry.TABLE_NAME, DiaDiarioEntry.FECHA + "= ?", new String[]{fecha});
    }


    // **** CURSOR ****
    public Cursor getCursorDiario(String fecha) throws SQLiteException {


        String where=null;
        String[] argWhere=null;
            where= DiaDiarioEntry.FECHA +"= ?";//"grupo=?"
            argWhere=new String[]{fecha};

        Cursor cursor = db.query(DiaDiarioEntry.TABLE_NAME, null, where, argWhere, null, null, null);
        //close();
        return cursor;
    }


    public static DiaDiario deCursorADiario(Cursor cursor) {


        int indiceColumna;
        //obtenemos la posicion de la columna id
        indiceColumna = cursor.getColumnIndex(DiaDiarioEntry._ID);

        indiceColumna = cursor.getColumnIndex(DiaDiarioEntry.FECHA);
        String fecha = cursor.getString(indiceColumna);

        Date f = fechaBDtoFecha(fecha);
        indiceColumna = cursor.getColumnIndex(DiaDiarioEntry.VALORACION);
        int valoracion = cursor.getInt(indiceColumna);

        indiceColumna = cursor.getColumnIndex(DiaDiarioEntry.RESUMEN);
        String resumen = cursor.getString(indiceColumna);

        indiceColumna = cursor.getColumnIndex(DiaDiarioEntry.CONTENIDO);
        String contenido = cursor.getString(indiceColumna);

        return new DiaDiario(f, valoracion, resumen, contenido);

    }

    // Clase Interna
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context){
            super(context, DB_NOMBRE, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){

            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TABLE);
        }
    }





}
