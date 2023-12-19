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
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class VistaReservaActivity extends AppCompatActivity {

    //Declaracion de los componentes
    protected TextView label1;
    protected Button boton2;
    protected Button boton3;
    protected Button boton4;
    protected Button boton5;
    protected Button boton6;

    private BaseDatosReservas bdr;
    private Intent pasarPantalla;
    private Bundle bundle;
    private String paquete1 = "";
    private String paquete2 = "";
    private String paquete3 = "";
    private String paquete4 = "";
    private String paquete5 = "";
    private String paquete6 = "";
    private String contenidoLabel = "";
    private String partes[];
    private int identificador = 0;

    protected BaseDatosReservas dbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_reserva);

        //Referenciar componentes
        label1 = (TextView) findViewById(R.id.vistaReservasTextBox01);

        boton2 = (Button) findViewById(R.id.vistaReservasTextButtonNavi);
        boton3 = (Button) findViewById(R.id.vistaReservasTextButtonEndTransaction);
        boton4 = (Button) findViewById(R.id.vistaReservasTextButtonCancelTransaction);
        boton5 = (Button) findViewById(R.id.vistaReservaBoton_inicio_reservas);
        boton6 = (Button) findViewById(R.id.vistaReservaBoton_volver_reservas);
        bundle=getIntent().getExtras();

        //LABEL1
        if (bundle != null) {
            //paquete1 = bundle.getString("id_reserva");
            paquete2 = bundle.getString("fecha");
            paquete3 = bundle.getString("hora");
            //paquete4 = bundle.getString("latitud");
            //paquete5 = bundle.getString("longitud");
            paquete6 = bundle.getString("detalles");

            //Double latitud = Double.parseDouble(paquete4);
            //Double longitud = Double.parseDouble(paquete5);
            //+"Dirección: "+obtenerDireccion(latitud, longitud)

            label1.setText(paquete2);
        }

        //LABEL2



        //NAVEGACIÓN
        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent pasarPantalla = new Intent(VistaReservaActivity.this, NavegacionActivity.class);
                finish();
                startActivity(pasarPantalla);*/
            }
        });

        //FINALIZAR TRANSACCION
        boton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        VistaReservaActivity.this);

                // set title
                alertDialogBuilder.setTitle("Finalizar transacción");

                // set dialog message
                alertDialogBuilder
                        .setMessage("¿Está seguro de que ha completado su reserva de aparcamiento?")
                        .setCancelable(false)
                        .setPositiveButton("Sí",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity
                                VistaReservaActivity.this.finish();
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
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

        //CANCELAR RESERVA
        boton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        VistaReservaActivity.this);

                // set title
                alertDialogBuilder.setTitle("Cancelar reserva");

                // set dialog message
                alertDialogBuilder
                        .setMessage("¿Está seguro de que quiere cancelar la reserva?")
                        .setCancelable(false)
                        .setPositiveButton("Sí",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                contenidoLabel = label1.getText().toString();
                                partes = contenidoLabel.split("-");
                                if (partes.length > 0) {
                                    identificador = Integer.parseInt(partes[0]);
                                    dbr.deleteReserva(identificador);
                                    Toast.makeText(VistaReservaActivity.this, "Reserva cancelada correctamente", Toast.LENGTH_SHORT).show();
                                    pasarPantalla = new Intent(VistaReservaActivity.this, MenuUsuarioActivity.class);
                                    finish();
                                    startActivity(pasarPantalla);
                                }
                                VistaReservaActivity.this.finish();
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

        //IR A INICIO
        boton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pasarPantalla = new Intent(VistaReservaActivity.this, MenuUsuarioActivity.class);
                finish();
                startActivity(pasarPantalla);
            }
        });

        //VOLVER
        boton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pasarPantalla = new Intent(VistaReservaActivity.this, MisReservasActivity.class);
                finish();
                startActivity(pasarPantalla);
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