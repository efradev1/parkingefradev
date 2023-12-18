package es.ifp.parking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.util.ArrayList;

public class MisReservasActivity extends AppCompatActivity {

    //Declaracion de los componentes
    protected TextView label1;
    protected TextView label2;
    protected TextView label3;
    protected Button boton1;
    protected Button boton2;
    protected ListView listaR;
    protected ListView listaV;

    protected ArrayList<String> listadoReservas = new ArrayList<String>();
    protected ArrayList<String> listadoVentas = new ArrayList<String>();
    protected ArrayAdapter<String> adaptador;
    protected BaseDatosUsuario db;
    protected BaseDatosVentas dbv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_reservas);

        //Referenciar componentes
        label1 = (TextView)  findViewById(R.id.label_title_reservas);
        label2 = (TextView) findViewById(R.id.label1_misReservas);
        label3 = (TextView) findViewById(R.id.label2_misReservas);
        boton1 = (Button) findViewById(R.id.vistaReservaBoton_inicio_reservas);
        boton2 = (Button) findViewById(R.id.vistaReservaBoton_volver_reservas);
        listaR = (ListView) findViewById(R.id.list_reservas);
        listaV = (ListView) findViewById(R.id.list_ventas);

        db= new BaseDatosUsuario(this);
        dbv= new BaseDatosVentas(this);

        //listado1 = db.get...
       listadoVentas = dbv.getAllReservas();

        adaptador = new ArrayAdapter<>(MisReservasActivity.this, android.R.layout.simple_list_item_1, listadoReservas);
        listaR.setAdapter(adaptador);
        adaptador = new ArrayAdapter<>(MisReservasActivity.this, android.R.layout.simple_list_item_1, listadoVentas);
        listaV.setAdapter(adaptador);

        //Listener de la lista de reservas
        listaR.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        //Listener de la lista de ventas
        listaV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



            }
        });

        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pasarPantalla = new Intent(MisReservasActivity.this, MenuUsuarioActivity.class);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pasarPantalla = new Intent(MisReservasActivity.this, MenuUsuarioActivity.class);
                finish();
                startActivity(pasarPantalla);
            }
        });
    }
}
