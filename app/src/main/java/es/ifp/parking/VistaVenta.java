package es.ifp.parking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class VistaVenta extends AppCompatActivity {

    protected TextView label1_venta;
    protected TextView label2_venta;
    protected TextView label3_venta;
    protected Button boton4_venta;
    protected Button boton5_venta;
    protected Button boton6_venta;
    protected Intent pasarPantalla;

    protected BaseDatosVentas dbv;
    protected UnaVenta reserva;
    private Bundle bundle;

    private String venta="";
  /*  private String partes[];
    private int identificador = 0;*/

    private int id_venta;
    private Double latitud;
    private Double longitud;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_venta);

        label1_venta=(TextView) findViewById(R.id.label1_venta);
        label2_venta=(TextView) findViewById(R.id.label2_venta);
        label3_venta=(TextView) findViewById(R.id.label3_venta);
        boton4_venta=(Button) findViewById(R.id.boton4_venta);
        boton5_venta=(Button) findViewById(R.id.boton5_venta);
        boton6_venta=(Button) findViewById(R.id.boton6_venta);

        dbv= new BaseDatosVentas(this);

        bundle=getIntent().getExtras();

        if (bundle!=null){
            venta=bundle.getString("fecha");
            id_venta=bundle.getInt("id_venta");

            reserva=dbv.getVenta(id_venta);

            latitud=reserva.getLatitud();
            longitud=reserva.getLongitud();

            String direccion = obtenerDireccion(latitud, longitud);

            label3_venta.setText("Los datos de tu reserva en venta son: "+ "\n" + venta + "\n" +
                    "La dirección de tu reserva en venta es: "+ "\n" + direccion);
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
                //CANCELAR LA RESERVA
        boton4_venta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        VistaVenta.this);

                // set title
                alertDialogBuilder.setTitle("Cancelar reserva");

                // set dialog message
                alertDialogBuilder
                        .setMessage("¿Está seguro de que quiere cancelar la reserva?")
                        .setCancelable(false)
                        .setPositiveButton("Sí",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                           dbv.deleteVenta(id_venta);

                                    Toast.makeText(VistaVenta.this, "Reserva cancelada correctamente", Toast.LENGTH_SHORT).show();
                                    pasarPantalla = new Intent(VistaVenta.this, MenuUsuarioActivity.class);
                                    finish();
                                    startActivity(pasarPantalla);

                                VistaVenta.this.finish();
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close.
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });
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