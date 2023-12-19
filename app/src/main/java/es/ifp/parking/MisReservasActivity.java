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
    protected String contenidoItem = "";
    private Intent pasarPantalla;
    private String partes[];

    protected ArrayList<String> listadoReservas = new ArrayList<String>();
    protected ArrayList<String> listadoVentas = new ArrayList<String>();
    protected ArrayAdapter<String> adaptador;
    private BaseDatosReservas dbr;
    private BaseDatosVentas dbv;

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

        //Referenciamos las bases de datos
        dbr= new BaseDatosReservas(this );
        dbv= new BaseDatosVentas(this);

        //Se asignan a los listados los métodos que devuelven los resultados necesarios
        listadoReservas = dbr.getAllReservas();
        listadoVentas = dbv.getAllVentas();

        //Creamos un ArrayAdapter
        adaptador = new ArrayAdapter<>(MisReservasActivity.this, android.R.layout.simple_list_item_1, listadoReservas);
        listaR.setAdapter(adaptador);
        adaptador = new ArrayAdapter<>(MisReservasActivity.this, android.R.layout.simple_list_item_1, listadoVentas);
        listaV.setAdapter(adaptador);

        //Listener de la lista de reservas
        listaR.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                contenidoItem = parent.getItemAtPosition(position).toString();
                partes=contenidoItem.split("-");
                if (partes.length>1)
                {
                    //Asignamos al objeto r de la clase UnaReserva los datos obtenidos de la bbdd, el id, la posición 0 del Array
                    UnaReserva r = dbr.getReserva(Integer.parseInt(partes[0]));

                    //Si r es distinto de null, selecciona la reserva y pasa los paquetes necesarios a la siguiente pantalla para que se muestren los detalles de la reserva
                    if(r!=null) {
                        int identificador = Integer.parseInt(partes[0]);
                        pasarPantalla = new Intent(MisReservasActivity.this, VistaReservaActivity.class);
                        pasarPantalla.putExtra("id_reserva", identificador);
                        pasarPantalla.putExtra("fecha", contenidoItem);

                        finish();
                        startActivity(pasarPantalla);
                    }
                }
            }
        });

        //Listener de la lista de ventas, hace lo mismo que el de arriba pero pasa a la pantalla detalles ventas
        listaV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                contenidoItem = parent.getItemAtPosition(position).toString();
                partes=contenidoItem.split("-");
                if (partes.length>1) {
                    UnaVenta v = dbv.getVenta(Integer.parseInt(partes[0]));

                    if (v != null) {
                      int identificador = Integer.parseInt(partes[0]);

                        pasarPantalla = new Intent(MisReservasActivity.this, VistaVenta.class);
                        pasarPantalla.putExtra("id_venta", identificador);
                        pasarPantalla.putExtra("fecha", contenidoItem);
                        finish();
                        startActivity(pasarPantalla);
                    }
                }}
        });

        //Botón inicio
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pasarPantalla = new Intent(MisReservasActivity.this, MenuUsuarioActivity.class);
                finish();
                startActivity(pasarPantalla);
            }
        });

        //Botón volver
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
