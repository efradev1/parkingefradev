package es.ifp.parking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import java.time.DateTimeException;
import java.util.Date;

public class DetallesVentaActivity extends AppCompatActivity {

    protected EditText fecha;
    protected EditText hora;
    protected EditText latitud;
    protected EditText longitud;

    private Date contenidoFecha;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_venta);
    }
}