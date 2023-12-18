package es.ifp.parking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class VistaVenta extends AppCompatActivity {

    protected TextView label1_venta;
    protected TextView label2_venta;
    protected TextView label3_venta;
    protected EditText caja1_venta;
    protected EditText caja2_venta;
    protected Button boton1_venta;
    protected Button boton2_venta;
    protected Button boton3_venta;
    protected Button boton4_venta;
    protected Button boton5_venta;
    protected Button boton6_venta;
    protected Intent pasarPantalla;

    protected BaseDatosVentas dbv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_venta);

        label1_venta=(TextView) findViewById(R.id.label1_venta);
        label2_venta=(TextView) findViewById(R.id.label2_venta);
        label3_venta=(TextView) findViewById(R.id.label3_venta);
        caja1_venta=(EditText) findViewById(R.id.caja1_venta);
        caja2_venta=(EditText) findViewById(R.id.caja2_venta);
        boton1_venta=(Button) findViewById(R.id.boton1_venta);
        boton2_venta=(Button) findViewById(R.id.boton2_venta);
        boton3_venta=(Button) findViewById(R.id.boton3_venta);
        boton4_venta=(Button) findViewById(R.id.boton4_venta);
        boton5_venta=(Button) findViewById(R.id.boton5_venta);
        boton6_venta=(Button) findViewById(R.id.boton6_venta);

        dbv= new BaseDatosVentas(this);









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

    }
}