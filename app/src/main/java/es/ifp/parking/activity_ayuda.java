package es.ifp.parking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class activity_ayuda extends AppCompatActivity {

    protected Button button1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);

        button1=(Button) findViewById(R.id.button1_ayuda);

        //VOLVER
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(activity_ayuda.this, "Redirigiendo", Toast.LENGTH_SHORT).show();
                Intent pasarPantalla= new Intent(activity_ayuda.this, MenuUsuarioActivity.class);
                finish();
                startActivity(pasarPantalla);
            }
        });

    }
}