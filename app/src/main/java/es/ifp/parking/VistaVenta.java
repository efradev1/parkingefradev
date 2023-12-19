package es.ifp.parking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class VistaVenta extends AppCompatActivity {

    protected TextView label1_venta;
    protected TextView label2_venta;
    protected TextView label3_venta;
    protected Button boton3_venta;
    protected Button boton4_venta;
    protected Button boton5_venta;
    protected Button boton6_venta;
    protected Intent pasarPantalla;

    protected BaseDatosVentas dbv;
    private Bundle bundle;

    private String reserva="";
    private String partes[];
    private int identificador = 0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_venta);

        label1_venta=(TextView) findViewById(R.id.label1_venta);
        label2_venta=(TextView) findViewById(R.id.label2_venta);
        label3_venta=(TextView) findViewById(R.id.label3_venta);

        boton3_venta=(Button) findViewById(R.id.boton3_venta);
        boton4_venta=(Button) findViewById(R.id.boton4_venta);
        boton5_venta=(Button) findViewById(R.id.boton5_venta);
        boton6_venta=(Button) findViewById(R.id.boton6_venta);

        dbv= new BaseDatosVentas(this);

       /* bundle=getIntent().getExtras();

        if (bundle!=null){
            reserva=bundle.getString("fecha");
            label3_venta.setText(reserva);
        }*/
        Intent intent = getIntent();
        if (intent != null) {
            int idReserva = intent.getIntExtra("id_reserva", 0);
            String fecha = intent.getStringExtra("fecha");
            String hora = intent.getStringExtra("hora");
            double latitud = intent.getDoubleExtra("latitud", 0.0);
            double longitud = intent.getDoubleExtra("longitud", 0.0);
            String detalles = intent.getStringExtra("detalles");

            String direccion = obtenerDireccion(latitud, longitud);

            label3_venta.setText(fecha + hora + direccion + detalles);
        }




            boton5_venta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pasarPantalla=new Intent(VistaVenta.this, MenuUsuarioActivity.class);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton6_venta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pasarPantalla=new Intent(VistaVenta.this, MisReservasActivity.class);
                finish();
                startActivity(pasarPantalla);

            }
        });
                /*CANCELAR LA RESERVA
        boton4_venta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pasarPantalla=new Intent(VistaVenta.this, MisReservasActivity.class);
                finish();
                startActivity(pasarPantalla);

            }
        });*/

    }
    private String obtenerDireccion(double latitud, double longitud) {
        String direccionString = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> direcciones = geocoder.getFromLocation(latitud, longitud, 1);
            if (direcciones != null && direcciones.size() > 0) {
                Address direccion = direcciones.get(0);
                StringBuilder direccionBuilder = new StringBuilder();

                for (int i = 0; i <= direccion.getMaxAddressLineIndex(); i++) {
                    direccionBuilder.append(direccion.getAddressLine(i)).append(" ");
                }
                direccionString = direccionBuilder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return direccionString;
    }
}