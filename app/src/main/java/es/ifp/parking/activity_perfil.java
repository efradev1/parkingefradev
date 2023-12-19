package es.ifp.parking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class activity_perfil extends AppCompatActivity {

    protected TextView label1;
    protected TextView label2;
    protected TextView label3;
    protected TextView label4;
    protected TextView label5;
    protected TextView label6;
    protected Button button1;
    protected Button button2;
    protected Button button3;
    private UnUsuario u;

    private BaseDatosUsuario bdu;

    protected ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        label1=(TextView) findViewById(R.id.label1_perfil);
        label2=(TextView) findViewById(R.id.label2_perfil);
        label3=(TextView) findViewById(R.id.label3_perfil);
        label4=(TextView) findViewById(R.id.label4_perfil);
        label5=(TextView) findViewById(R.id.label5_perfil);
        label6=(TextView) findViewById(R.id.label6_perfil);

        button1=(Button) findViewById(R.id.button1_perfil);
        button2=(Button) findViewById(R.id.button2_perfil);
        button3=(Button) findViewById(R.id.button3_perfil);
        imageView1=(ImageView) findViewById(R.id.imageView1_perfil);

        bdu = new BaseDatosUsuario(this);

        SharedPreferences preferences = getSharedPreferences("usuario_info", Context.MODE_PRIVATE);
        String email = preferences.getString("email", "");
        String password = preferences.getString("password", "");

        u = bdu.getUsuario(email, password);

        String nombre = u.getNombre();
        label2.setText(nombre);

        String apellidos = u.getApellido();
        label3.setText(apellidos);

        String email2 = u.getEmail();
        label4.setText(email2);

        String telefono = u.getTelefono();
        label5.setText(telefono);

        String cp = u.getCp();
        label6.setText(cp);

        //MODIFICAR PERFIL
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(activity_perfil.this, "Redirigiendo", Toast.LENGTH_SHORT).show();
                Intent pasarPantalla= new Intent(activity_perfil.this, activity_modificar_perfil.class);
                finish();
                startActivity(pasarPantalla);
            }
        });

        //INICIO
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(activity_perfil.this, "Redirigiendo", Toast.LENGTH_SHORT).show();
                Intent pasarPantalla= new Intent(activity_perfil.this, MenuUsuarioActivity.class);
                finish();
                startActivity(pasarPantalla);
            }
        });

        //VOLVER
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(activity_perfil.this, "Redirigiendo", Toast.LENGTH_SHORT).show();
                Intent pasarPantalla= new Intent(activity_perfil.this, activity_mi_espacio.class);
                finish();
                startActivity(pasarPantalla);
            }
        });

    }
}