package es.ifp.parking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class BaseDatosReservas extends SQLiteOpenHelper {

    protected SQLiteDatabase db;


    public BaseDatosReservas(Context context) {
        // Constructor de la clase, llamando al constructor de la clase base SQLiteOpenHelper

        super(context,"Reservas", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Método llamado al crear la base de datos, aquí se define la estructura de la tabla
        db.execSQL("CREATE table reserva(id_reserva integer primary key autoincrement not null,id_usuario integer, fecha text,hora text, latitud real, longitud real, detalles text, FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Método llamado cuando se actualiza la base de datos, en este caso, se elimina la tabla si existe
        db.execSQL("DROP TABLE IF EXISTS reserva");

    }

    public void insertReserva(int id_usuario, String fecha, String hora, double latitud, double longitud, String detalles){
        // Método para insertar una nueva reserva en la base de datos
        db=this.getReadableDatabase();
        db.execSQL("INSERT INTO reserva(id_usuario, fecha, hora, latitud, longitud, detalles) " +
                "VALUES (" + id_usuario + ",'" + fecha + "','" + hora + "'," + latitud + "," + longitud + ",'" + detalles + "')");
        db.close();
    }

    public void deleteReserva(int id_reserva){
        // Método para eliminar una reserva específica de la base de datos
        db=this.getWritableDatabase();
        db.execSQL("DELETE FROM reserva WHERE id_reserva=" +id_reserva);
        db.close();
    }
    public int numReservas(){
        // Método para obtener la cantidad de registros en la tabla "reserva"

        int num=0;
        db=this.getReadableDatabase();
        num=(int) DatabaseUtils.queryNumEntries(db,"reserva");
        return num;

    }

    @SuppressLint("Range")
    public UnaReserva getReserva(int id_reserva){
        // Método para obtener una reserva específica por su id_reserva
        UnaReserva r= null;
        Cursor res= null;
        db= this.getReadableDatabase();
        if(numReservas()>0) {
            res = db.rawQuery("SELECT * FROM reserva WHERE id_reserva= ?", new String[]{String.valueOf(id_reserva)});
            if (res.moveToFirst()) {
                @SuppressLint("Range") int storedidr = res.getInt(res.getColumnIndex("id_reserva"));
                if (storedidr==(id_reserva)) {
                    r = new UnaReserva(
                            storedidr,
                            res.getInt(res.getColumnIndex("id_usuario")),
                            res.getString(res.getColumnIndex("fecha")),
                            res.getString(res.getColumnIndex("hora")),
                            res.getDouble(res.getColumnIndex("latitud")),
                            res.getDouble(res.getColumnIndex("longitud")),
                            res.getString(res.getColumnIndex("detalles"))
                    );
                }
            }
        }
        res.close();
        return r;
    }
    @SuppressLint("Range")
    public int obtenerIdReserva(double latitud, double longitud) {
        // Método para obtener el id_reserva correspondiente a una reserva por su latitud y longitud
        int idReserva = -1;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {

            String query = "SELECT id_reserva FROM reserva WHERE latitud = ? AND longitud = ?";
            String[] selectionArgs = {String.valueOf(latitud), String.valueOf(longitud)};
            cursor = db.rawQuery(query, selectionArgs);

            if (cursor.moveToFirst()) {
                idReserva = cursor.getInt(cursor.getColumnIndex("id_reserva"));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return idReserva;
    }
    @SuppressLint("Range")
    public ArrayList<String> getAllReservas(){
        // Método para obtener una lista de cadenas que representan todas las reservas en la base de datos

        ArrayList<String> listaReservas=new ArrayList<String>();
        String contenido="";
        Cursor res=null;
        db = this.getReadableDatabase();
        if(numReservas()>0) {
            res = db.rawQuery("SELECT * FROM reserva ORDER BY fecha ASC", null);
            res.moveToFirst();
            while (res.isAfterLast() == false) {

                contenido =res.getString(res.getColumnIndex("id_reserva"))+"-"+"\n"
                        + res.getString(res.getColumnIndex("fecha"))+"-"+res.getString(res.getColumnIndex("hora"))+"\n"
                        +res.getDouble(res.getColumnIndex("latitud"))+"-"+res.getDouble(res.getColumnIndex("longitud"))+"\n"
                        +res.getString(res.getColumnIndex("detalles"));
                listaReservas.add(contenido);
                res.moveToNext();

            }
        }
        return listaReservas;

    }

    @SuppressLint("Range")
    public ArrayList<String> getAllReservasSimple(){
        // Método para obtener una lista de cadenas que representan todas las reservas (simplificadas) en la base de datos
        ArrayList<String> listaReservas=new ArrayList<String>();
        String contenido="";
        Cursor res=null;
        db = this.getReadableDatabase();
        if(numReservas()>0) {
            res = db.rawQuery("SELECT * FROM reserva ORDER BY fecha ASC", null);
            res.moveToFirst();
            while (res.isAfterLast() == false) {

                contenido =res.getString(res.getColumnIndex("id_reserva"))+"\n"
                        + res.getString(res.getColumnIndex("fecha"))+"-"+res.getString(res.getColumnIndex("hora"));
                listaReservas.add(contenido);
                res.moveToNext();

            }
        }
        return listaReservas;

    }


}