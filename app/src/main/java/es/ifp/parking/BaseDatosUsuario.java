package es.ifp.parking;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class BaseDatosUsuario extends SQLiteOpenHelper {

    protected SQLiteDatabase db;


    public BaseDatosUsuario(Context context) {
        // Constructor de la clase, llamando al constructor de la clase base SQLiteOpenHelper

        super(context,"Usuario", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Método llamado al crear la base de datos, aquí se define la estructura de la tabla "usuario"
        db.execSQL("CREATE table usuario(id_usuario integer primary key autoincrement not null, nombre text,apellido text, email text, telefono text,cp text, marcaC text, modeloC text, matricula text, password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Método llamado cuando se actualiza la base de datos, en este caso, se elimina la tabla si existe y se crea de nuevo
        db.execSQL("DROP TABLE IF EXISTS usuario");
        onCreate(db);
    }

    public void updateUsuario(String nombre, String apellido, String email, String telefono, String cp, String password) {
        // Método para actualizar la información de un usuario en la base de datos
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("apellido", apellido);
        values.put("email", email);
        values.put("telefono", telefono);
        values.put("cp", cp);
        values.put("password", password);

        db.update("usuario", values, null, null);
        db.close();
    }

    public void insertUsuario(String nombre, String apellido,  String email, String telefono, String cp, String marcaC, String modeloC, String matricula ,String password){
        // Método para insertar un nuevo usuario en la base de datos
        db=this.getReadableDatabase();
        db.execSQL("INSERT INTO usuario(nombre, apellido, email, telefono,  cp, marcaC, modeloC, matricula, password) VALUES ('"+nombre+"','"+apellido+"','"+email+"','"+telefono+"','"+cp+"','"+marcaC+"','"+modeloC+"','"+matricula+"','"+password+"')");
        db.close();
    }

    public void deleteUsuario(String email){
        // Método para eliminar un usuario específico de la base de datos por su email
        db=this.getWritableDatabase();
        db.execSQL("DELETE FROM usuario WHERE email=" +email);
        db.close();
    }
    public int numUsuarios(){
        // Método para obtener la cantidad de registros en la tabla "usuario"

        int num=0;
        db=this.getReadableDatabase();
        num=(int) DatabaseUtils.queryNumEntries(db,"usuario");
        return num;

    }

    @SuppressLint("Range")
    public UnUsuario getUsuario(String email, String password){
        // Método para obtener un usuario específico por su email y contraseña
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
    @SuppressLint("Range")
    public boolean UnUsuarioV(String email, String password){
        // Método para verificar si las credenciales de un usuario son válidas
        UnUsuario u= null;
        boolean credencialesV= false;
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
                    credencialesV=true;
                }
            }
        }

        return credencialesV;
    }

    @SuppressLint("Range")
    public boolean UnUsuarioVemail(String email){
        // Método para verificar si existe un usuario con un email específico
        UnUsuario u= null;
        boolean credencialesVemail= false;
        Cursor res= null;
        db= this.getReadableDatabase();
        if(numUsuarios()>=0) {
            res = db.rawQuery("SELECT * FROM usuario WHERE email= ?", new String[]{email});
            if(res.moveToFirst()){
                u = new UnUsuario(
                        res.getString(res.getColumnIndex("nombre")),
                        res.getString(res.getColumnIndex("apellido")),
                        res.getString(res.getColumnIndex("email")),
                        res.getString(res.getColumnIndex("telefono")),
                        res.getString(res.getColumnIndex("cp")),
                        res.getString(res.getColumnIndex("marcaC")),
                        res.getString(res.getColumnIndex("modeloC")),
                        res.getString(res.getColumnIndex("matricula")),
                        res.getString(res.getColumnIndex("password"))
                );
                credencialesVemail=true;
            }

        }
        return credencialesVemail;
    }
    @SuppressLint("Range")
    public int obtenerIdUsuario(String email, String password) {
        // Método para obtener el id_usuario correspondiente a un usuario por su email y contraseña
        int idUsuario = -1;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {

            String query = "SELECT id_usuario FROM usuario WHERE email = ? AND password = ?";
            cursor = db.rawQuery(query, new String[]{email, password});

            if (cursor.moveToFirst()) {
                idUsuario = cursor.getInt(cursor.getColumnIndex("id_usuario"));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return idUsuario;
    }





}