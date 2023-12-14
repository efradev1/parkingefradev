package es.ifp.parking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatosVentas extends SQLiteOpenHelper {

    protected SQLiteDatabase db;


    public BaseDatosVentas(Context context) {

        super(context,"Ventas", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE table venta(id_reserva integer primary key autoincrement not null,id_usuario integer, fecha text,hora text, latitud real, longitud real, detalles text, FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS venta");

    }

    public void insertVenta(String nombre, String apellido,  String email, String telefono, String cp, String marcaC, String modeloC, String matricula ,String password){

        db=this.getReadableDatabase();
        db.execSQL("INSERT INTO usuario(nombre, apellido, email, telefono,  cp, marcaC, modeloC, matricula, password) VALUES ('"+nombre+"','"+apellido+"','"+email+"','"+telefono+"','"+cp+"','"+marcaC+"','"+modeloC+"','"+matricula+"','"+password+"')");
        db.close();
    }

    public void deleteUsuario(String email){
        db=this.getWritableDatabase();
        db.execSQL("DELETE FROM usuario WHERE email=" +email);
        db.close();
    }
    public int numUsuarios(){

        int num=0;
        db=this.getReadableDatabase();
        num=(int) DatabaseUtils.queryNumEntries(db,"usuario");
        return num;

    }

    @SuppressLint("Range")
    public UnUsuario getUsuario(String email, String password){
        UnUsuario u= null;
        Cursor res= null;
        db= this.getReadableDatabase();
        if(numUsuarios()>0) {
            res = db.rawQuery("SELECT * FROM usuario WHERE email= ?", new String[]{email});
            if (res.moveToFirst()) {
                @SuppressLint("Range") String storedPass = res.getString(res.getColumnIndex("password"));
                if (storedPass.equals(password)) {
                    u = new UnUsuario(
                            res.getString(res.getColumnIndex("nombre")),
                            res.getString(res.getColumnIndex("apellido")),
                            res.getString(res.getColumnIndex("email")),
                            res.getString(res.getColumnIndex("telefono")),
                            res.getString(res.getColumnIndex("cp")),
                            res.getString(res.getColumnIndex("marcaC")),
                            res.getString(res.getColumnIndex("modeloC")),
                            res.getString(res.getColumnIndex("matricula")),
                            storedPass
                    );
                }

            }
        }
        res.close();
        return u;
    }

}