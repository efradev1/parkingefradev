package es.ifp.parking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

    protected ImageView imageView1;

    private activity_modificar_perfil activity_modificar_perfil;

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

        activity_modificar_perfil = new activity_modificar_perfil();

        String nombreModificado = activity_modificar_perfil.editText1.getText().toString();
        label2.setText(nombreModificado);

        String apellidoModificado = activity_modificar_perfil.editText2.getText().toString();
        label3.setText(apellidoModificado);

        String emailModificado = activity_modificar_perfil.editText3.getText().toString();
        label4.setText(emailModificado);

        String telefonoModificado = activity_modificar_perfil.editText4.getText().toString();
        label5.setText(telefonoModificado);

        String cpModificado = activity_modificar_perfil.editText5.getText().toString();
        label6.setText(cpModificado);

        imageView1.setImageDrawable(activity_modificar_perfil.imageView1.getDrawable());

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