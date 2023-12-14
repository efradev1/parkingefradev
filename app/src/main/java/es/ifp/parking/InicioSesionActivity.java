package es.ifp.parking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class InicioSesionActivity extends AppCompatActivity {

    protected EditText label1;
    protected EditText label2;
    protected Button boton1;
    protected Button boton2;
    protected Button boton3;
    protected ImageView logo;


    private String contenidoLabel1="";
    private String contenidoLabel2="";
    protected BaseDatosUsuario db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        label1=(EditText) findViewById(R.id.label1_InicioSesion);
        label2=(EditText) findViewById(R.id.label2_InicioSesion);
        boton1=(Button) findViewById(R.id.boton1_InicioSesion);
        boton2=(Button) findViewById(R.id.boton2_InicioSesion);
        boton3=(Button) findViewById(R.id.boton3_InicioSesion);
        logo=(ImageView) findViewById(R.id.imagen_InicioSesion);
        db= new BaseDatosUsuario(this );

        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contenidoLabel1= label1.getText().toString();
                contenidoLabel2=label2.getText().toString();
                if(contenidoLabel1.equals("")){
                    Toast.makeText(InicioSesionActivity.this, "Email obligatorio", Toast.LENGTH_LONG).show();
                }else if(contenidoLabel2.equals("")){
                    Toast.makeText(InicioSesionActivity.this, "Contraseña obligatoria", Toast.LENGTH_LONG).show();
                }else{
                    boolean credencialesEmail = db.UnUsuarioVemail(contenidoLabel1);

                    if(credencialesEmail==true){
                        boolean credencialesCorrectas= db.UnUsuarioV(contenidoLabel1,contenidoLabel2);

                        if(credencialesCorrectas==true){
                            Toast.makeText(InicioSesionActivity.this,"Conectado", Toast.LENGTH_LONG).show();
                            Intent pasarPantalla= new Intent(InicioSesionActivity.this, MenuUsuarioActivity.class);
                            finish();
                            startActivity(pasarPantalla);
                        }else{
                            Toast.makeText(InicioSesionActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(InicioSesionActivity.this,"Email no registrado", Toast.LENGTH_LONG).show();
                    }

                }

            }
        });

        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(InicioSesionActivity.this, "Registro", Toast.LENGTH_SHORT).show();
                Intent pasarPantalla= new Intent(InicioSesionActivity.this, RegistroUsuario.class);
                finish();
                startActivity(pasarPantalla);

            }
        });

        boton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pasarPantalla= new Intent(InicioSesionActivity.this, OlvidoUsuarioActivity.class);
                finish();
                startActivity(pasarPantalla);
                Toast.makeText(InicioSesionActivity.this, "Redirigiendo", Toast.LENGTH_SHORT).show();

            }
        });
    }
}