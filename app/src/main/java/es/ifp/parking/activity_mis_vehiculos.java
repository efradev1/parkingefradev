package es.ifp.parking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class activity_mis_vehiculos extends AppCompatActivity {

    protected TextView label1;
    protected TextView label2;

    protected Button button1;
    protected Button button2;
    protected Button button3;
    protected Button button4;

    protected ListView listaVehiculos;
    protected ArrayList<String> listadoVehiculos = new ArrayList<String>();
    protected ArrayAdapter<String> adaptador;

    protected BaseDatosUsuario db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_vehiculos);

        button1=(Button) findViewById(R.id.button1_vehiculos);
        button2=(Button) findViewById(R.id.button2_vehiculos);
        button3=(Button) findViewById(R.id.button3_vehiculos);
        button4=(Button) findViewById(R.id.button4_vehiculos);
        listaVehiculos= (ListView) findViewById(R.id.list_vehiculos1);

        db= new BaseDatosUsuario(this );

        adaptador = new ArrayAdapter<>(activity_mis_vehiculos.this, android.R.layout.simple_list_item_1, listadoVehiculos);
        listaVehiculos.setAdapter(adaptador);

        listaVehiculos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        /* AÑADIR VEHICULO
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ********************
            }
        });*/

        /* INICIO OK
       button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(activity_mis_vehiculos.this, "Redirigiendo", Toast.LENGTH_SHORT).show();
                Intent pasarPantalla= new Intent(activity_mis_vehiculos.this, MenuUsuarioActivity.class);
                finish();
                startActivity(pasarPantalla);
            }
        });*/

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(activity_mis_vehiculos.this, "Redirigiendo", Toast.LENGTH_SHORT).show();
                Intent pasarPantalla= new Intent(activity_mis_vehiculos.this, activity_mi_espacio.class);
                finish();
                startActivity(pasarPantalla);
            }
        });

        /* BORRAR VEHICULO
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ******************
            }
        });*/

    }
}