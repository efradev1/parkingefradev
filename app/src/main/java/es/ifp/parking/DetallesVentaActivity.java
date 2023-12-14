package es.ifp.parking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.osmdroid.util.GeoPoint;

import java.sql.Time;
import java.time.DateTimeException;
import java.util.Date;

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



        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                contenidoFecha=fechaEditText.getText().toString();
                contenidoHora=horaEditText.getText().toString();
                contenidoDetalles=detallesEditText.getText().toString();
                Intent intent= getIntent();
                if(intent != null){
                    double latitud= intent.getDoubleExtra("latitud",0.0);
                    double longitud=intent.getDoubleExtra("longitud",0.0);
                    latitudEditText.setText(String.valueOf(latitud));
                    longitudEditText.setText(String.valueOf(longitud));
                }
                mostrarDialogoConfirmacion();

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
    private void mostrarDialogoConfirmacion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Está seguro?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences preferences = getSharedPreferences("usuario_info", Context.MODE_PRIVATE);
                String email= preferences.getString("email","");
                String password=preferences.getString("password","");
                int id_usuario= bd.obtenerIdUsuario(email,password);
                db.insertVenta(id_usuario,contenidoFecha,contenidoHora,contenidoLatitud,contenidoLongitud,contenidoDetalles);
                Toast.makeText(DetallesVentaActivity.this,"Plaza anunciada", Toast.LENGTH_LONG).show();
                Intent pasarPantalla = new Intent(DetallesVentaActivity.this, MisReservasActivity.class);
                finish();
                startActivity(pasarPantalla);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // No hacer nada, simplemente cerrar el diálogo
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}