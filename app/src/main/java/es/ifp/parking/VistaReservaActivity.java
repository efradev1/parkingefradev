package es.ifp.parking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class VistaReservaActivity extends AppCompatActivity {

    //Declaracion de los componentes
    protected TextView label1;
    protected TextView label2;
    protected Button boton1;
    protected Button boton2;
    protected Button boton3;
    protected Button boton4;
    protected Button boton5;
    protected Button boton6;
    protected Button boton7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_reserva);

        //Referenciar componentes
        label1 = (TextView) findViewById(R.id.vistaReservasTextBox01);
        label2 = (TextView) findViewById(R.id.vistaReservasTextBox02);
        boton1 = (Button) findViewById(R.id.vistaReservasTextButtonChat);
        boton2 = (Button) findViewById(R.id.vistaReservasTextButtonNavi);
        boton3 = (Button) findViewById(R.id.vistaReservasTextButtonEndTransaction);
        boton4 = (Button) findViewById(R.id.vistaReservasTextButtonCancelTransaction);
        boton5 = (Button) findViewById(R.id.vistaReservaBoton_inicio_reservas);
        boton6 = (Button) findViewById(R.id.vistaReservaBoton_volver_reservas);

        //CHAT
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //HACEMOS CHAT?
            }
        });

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
                alertDialogBuilder.setTitle("");

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
                alertDialogBuilder.setTitle("");

                // set dialog message
                alertDialogBuilder
                        .setMessage("¿Está seguro de que quiere cancelar la reserva?")
                        .setCancelable(false)
                        .setPositiveButton("Sí",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, cancel the booking
                                // current activity
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
}