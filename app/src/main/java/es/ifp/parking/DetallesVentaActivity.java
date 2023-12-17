package es.ifp.parking;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetallesVentaActivity extends AppCompatActivity {

    protected EditText fechaEditText;
    protected EditText horaEditText;
    protected EditText latitudEditText;
    protected EditText longitudEditText;
    protected EditText detallesEditText;
    protected Button volver;
    protected Button guardar;

    private String contenidoFecha= "";
    private String contenidoHora="";
    private Double contenidoLatitud= null;
    private Double contenidoLongitud=null;
    private String contenidoDetalles="";

    protected BaseDatosVentas db;
    protected BaseDatosUsuario bd;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_venta);

        fechaEditText=findViewById(R.id.editTextDate_PreguntaDia_DetallesVentaActivity);
        horaEditText=findViewById(R.id.editTextTime_Hora_DetallesVentaActivity);
        latitudEditText=findViewById(R.id.editText_Latitud_DetallesVentaActivity);
        longitudEditText=findViewById(R.id.editText_Longitud_DetallesVentaActivity);
        detallesEditText=findViewById(R.id.editText_Detalles_DetallesVentaActivity);
        volver=findViewById(R.id.botonVolver_DetallesVentaActivity);
        guardar=findViewById(R.id.botonGuardar_DetallesVentanaActivity);
        db= new BaseDatosVentas(this );
        bd= new BaseDatosUsuario(this);
        Intent intent= getIntent();
        if(intent != null){
            double latitud= intent.getDoubleExtra("latitud",0.0);
            double longitud=intent.getDoubleExtra("longitud",0.0);
            contenidoLatitud= latitud;
            contenidoLongitud= longitud;
            latitudEditText.setText(String.valueOf(latitud));
            longitudEditText.setText(String.valueOf(longitud));
        }
        latitudEditText.setEnabled(false);
        longitudEditText.setEnabled(false);


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date fechaActual = new Date();
                long horaActual = fechaActual.getTime();

                Date fechaUsuario = obtenerFechaYHoraIngresadas();
                long horaUsuario = fechaUsuario.getTime();

                if (fechaUsuario.compareTo(fechaActual) >= 0) {
                    if (horaUsuario > horaActual) {
                        contenidoHora = horaEditText.getText().toString();
                        contenidoFecha = fechaEditText.getText().toString();
                        contenidoDetalles = detallesEditText.getText().toString();
                        mostrarDialogoConfirmacion();
                    } else {
                        Toast.makeText(DetallesVentaActivity.this, "La fecha y hora deben ser posteriores",
                                +Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(DetallesVentaActivity.this, "La fecha y hora deben ser posteriores",
                            +Toast.LENGTH_LONG).show();
                }

            }
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pasarPantalla = new Intent(DetallesVentaActivity.this, VenderActivity.class);
                finish();
                startActivity(pasarPantalla);

            }
        });

    }
    private Date obtenerFechaYHoraIngresadas(){
        String fechaString = fechaEditText.getText().toString();
        String horaString = horaEditText.getText().toString();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            String fechaHoraString = fechaString +" "+ horaString;
            return dateFormat.parse(fechaHoraString);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }

    }
    private String obtenerDireccion(double latitud, double longitud){
        String direccionString = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> direcciones = geocoder.getFromLocation(latitud, longitud,1);
            if(direcciones !=null && direcciones.size()>0){
                Address direccion = direcciones.get(0);
                StringBuilder direccionBuilder = new StringBuilder();

                for(int i = 0; i<= direccion.getMaxAddressLineIndex(); i++){
                    direccionBuilder.append(direccion.getAddressLine(i)).append(" ");
                }
                direccionString = direccionBuilder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return direccionString;
    }
    private void mostrarDialogoConfirmacion() {
        String direccion = obtenerDireccion(contenidoLatitud, contenidoLongitud);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Está seguro que esta es la ubicacion \n"
                + direccion +"?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences preferences = getSharedPreferences("usuario_info", Context.MODE_PRIVATE);
                String email= preferences.getString("email","");
                String password=preferences.getString("password","");
                int id_usuario= bd.obtenerIdUsuario(email,password);
                db.insertVenta(id_usuario,contenidoFecha,contenidoHora,contenidoLatitud,contenidoLongitud,contenidoDetalles);
                Toast toast= Toast.makeText(DetallesVentaActivity.this,"Tu plaza ha sido registrada con éxito." +
                        "Puede ver los detalles en Mis Reservas.",Toast.LENGTH_LONG);
                         toast.setGravity(Gravity.CENTER,0,0);
                         toast.show();

                Intent pasarPantalla = new Intent(DetallesVentaActivity.this, MenuUsuarioActivity.class);
                finish();
                startActivity(pasarPantalla);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}