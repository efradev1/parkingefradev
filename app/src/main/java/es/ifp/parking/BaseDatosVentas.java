package es.ifp.parking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BaseDatosVentas extends SQLiteOpenHelper {

    protected SQLiteDatabase db;


    public BaseDatosVentas(Context context) {

        super(context,"Ventas", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE table venta(id_venta integer primary key autoincrement not null,id_usuario integer, fecha text,hora text, latitud real, longitud real, detalles text, FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS venta");

    }

    public void insertVenta(int id_usuario, String fecha, String hora, double latitud, double longitud, String detalles){

        db=this.getReadableDatabase();
        db.execSQL("INSERT INTO venta(id_usuario, fecha, hora, latitud, longitud, detalles) " +
                "VALUES (" + id_usuario + ",'" + fecha + "','" + hora + "'," + latitud + "," + longitud + ",'" + detalles + "')");
        db.close();
    }

    public void deleteVenta(int id_venta){
        db=this.getWritableDatabase();
        db.execSQL("DELETE FROM venta WHERE id_venta=" +id_venta);
        db.close();
    }
    public int numVentas(){

        int num=0;
        db=this.getReadableDatabase();
        num=(int) DatabaseUtils.queryNumEntries(db,"venta");
        return num;

    }

    @SuppressLint("Range")
    public UnaVenta getVenta(int id_venta){
        UnaVenta v= null;
        Cursor res= null;
        db= this.getReadableDatabase();
        if(numVentas()>0) {
            res = db.rawQuery("SELECT * FROM venta WHERE id_venta= ?", new String[]{String.valueOf(id_venta)});
            if (res.moveToFirst()) {
                @SuppressLint("Range") int storedidv = res.getInt(res.getColumnIndex("id_venta"));
                if (storedidv==(id_venta)) {
                    v = new UnaVenta(
                            storedidv,
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
        return v;
    }

    public List<UnaVenta> obtenerTodasLasVentas(){

        List<UnaVenta> ventas = new ArrayList<>();
        db= this.getReadableDatabase();

        Cursor res= db.rawQuery("SELECT * FROM venta", null);
        while(res.moveToNext()){
            @SuppressLint("Range") UnaVenta venta = new UnaVenta(
                    res.getInt(res.getColumnIndex("id_venta")),
                    res.getInt(res.getColumnIndex("id_usuario")),
                    res.getString(res.getColumnIndex("fecha")),
                    res.getString(res.getColumnIndex("hora")),
                    res.getDouble(res.getColumnIndex("latitud")),
                    res.getDouble(res.getColumnIndex("longitud")),
                    res.getString(res.getColumnIndex("detalles"))
            );
            ventas.add(venta);
        }
        res.close();
        return ventas;
    }
    @SuppressLint("Range")
    public int obtenerIdVenta(double latitud, double longitud) {
        int idVenta = -1;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {

            String query = "SELECT id_venta FROM venta WHERE latitud = ? AND longitud = ?";
            String[] selectionArgs = {String.valueOf(latitud), String.valueOf(longitud)};
            cursor = db.rawQuery(query, selectionArgs);

            if (cursor.moveToFirst()) {
                idVenta = cursor.getInt(cursor.getColumnIndex("id_venta"));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return idVenta;
    }

    public int numReservas(){

        int num=0;
        db=this.getReadableDatabase();
        num=(int) DatabaseUtils.queryNumEntries(db,"reserva");
        return num;

    }

    @SuppressLint("Range")
    public ArrayList<String> getAllReservas(){

        ArrayList<String> listaReservas=new ArrayList<String>();
        String contenido="";
        Cursor res=null;
        db = this.getReadableDatabase();
        if(numReservas()>0) {
            res = db.rawQuery("SELECT * FROM reserva ORDER BY fecha ASC", null);
            res.moveToFirst();
            while (res.isAfterLast() == false) {

                contenido =res.getString(res.getColumnIndex("id_reserva"))+"\n"
                        + res.getString(res.getColumnIndex("fecha"))+"-"+res.getString(res.getColumnIndex("hora"))+"\n"
                        +res.getDouble(res.getColumnIndex("latitud"))+"-"+res.getDouble(res.getColumnIndex("longitud"))+"\n"
                        +res.getString(res.getColumnIndex("detalles"));
                listaReservas.add(contenido);
                res.moveToNext();

            }
        }
        return listaReservas;

    }

}