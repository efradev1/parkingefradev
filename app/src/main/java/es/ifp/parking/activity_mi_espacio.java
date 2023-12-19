package es.ifp.parking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class activity_mi_espacio extends AppCompatActivity {

    protected TextView label1;

    protected Button button1;
    protected Button button2;
    protected Button button3;
    protected Button button4;
    protected Button button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_espacio);

        button1=(Button) findViewById(R.id.button1_espacio);
        button2=(Button) findViewById(R.id.button2_espacio);
        button3=(Button) findViewById(R.id.button3_espacio);
        button4=(Button) findViewById(R.id.button4_espacio);
        button5=(Button) findViewById(R.id.button4_espacio);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(activity_mi_espacio.this, "Redirigiendo", Toast.LENGTH_SHORT).show();
                Intent pasarPantalla= new Intent(activity_mi_espacio.this, activity_perfil.class);
                finish();
                startActivity(pasarPantalla);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(activity_mi_espacio.this, "Redirigiendo", Toast.LENGTH_SHORT).show();
                Intent pasarPantalla= new Intent(activity_mi_espacio.this, activity_mis_vehiculos.class);
                finish();
                startActivity(pasarPantalla);
            }
        });

        //HISTORIAL DE TRANSACCIONES
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(activity_mi_espacio.this, "Redirigiendo", Toast.LENGTH_SHORT).show();
                Intent pasarPantalla= new Intent(activity_mi_espacio.this, MisReservasActivity.class);
                finish();
                startActivity(pasarPantalla);
            }
        });

        /*//CERRAR SESIÓN
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            cerrarSesion();
            }

           private void cerrarSesion() {

            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            }
        });*/

        //VOLVER (AL MENU PRINICPAL)
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(activity_mi_espacio.this, "Redirigiendo", Toast.LENGTH_SHORT).show();
                Intent pasarPantalla= new Intent(activity_mi_espacio.this, MenuUsuarioActivity.class);
                finish();
                startActivity(pasarPantalla);
            }
        });

    }}

